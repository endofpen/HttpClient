import kotlinx.coroutines.*
import org.services.reader.parser.XmlParser
import org.services.reader.validation.XmlValidator
import java.io.File
import kotlin.coroutines.cancellation.CancellationException

class XmlObserver(private val parser: XmlParser, private val validator: XmlValidator) {
    private var job: Job? = null
    private val seenFiles = mutableSetOf<File>()
    fun start(directory: String, intervalMillis: Long) {
        if (job?.isActive == true) {
            println("Job is already running!")
            return
        }

        job = CoroutineScope(Dispatchers.Default).launch {
            println("XmlObserver started.")
            try {
                while (isActive) {
                    println("Job is already running!")
                    val currentFiles = File(directory).listFiles()?.toList() ?: emptyList()
                    println("Found files: ${currentFiles.size}")
                    val newFiles = currentFiles.filter { it.name.endsWith(".xml") && it !in seenFiles }
                    println("New files: ${newFiles.size}")
                    newFiles.forEach { processFile(it) }

                    seenFiles.addAll(newFiles)
                    delay(intervalMillis) // Wait before checking again
                }
            } catch (e: CancellationException) {
                println("Job was cancelled.")
            } catch (e: Exception) {
                println("Exception while observating directory: ${e.message}")
            } finally {
                println("Job finished.")
            }
        }
    }

    fun cancel() {
        if (job?.isActive == true) {
            job?.cancel()
            println("Job cancellation requested.")
        } else {
            println("No active job to cancel.")
        }
    }

    fun isAlive(): Boolean {
        return if (job == null) false
        else job!!.isActive
    }

    private fun processFile(file: File) {
        val isValid = validator.validateXML(file.absolutePath)
        if (isValid) {
            println("Validation status is true")
            val company = parser.parse(file.absolutePath)
            println(
                """
                This is the company: ${company.name}
                The address is:${company.address}
                The company has ${company.departments.size} departments.
            """.trimIndent()
            )
        }
    }
}
