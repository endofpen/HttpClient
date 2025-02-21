import kotlinx.coroutines.runBlocking
import org.services.reader.parser.XmlParser
import org.services.reader.validation.XmlValidator
import java.io.File

fun main() = runBlocking {
    val xsdFilePath = "src/main/resources/company.xsd"
    val xmlValidator = XmlValidator(File(xsdFilePath))
    val xmlParser = XmlParser()
    val observer = XmlObserver(xmlParser, xmlValidator)
    val xmlFolder = "src/main/resources"

    observer.start(xmlFolder, 10000)
    println("Observer started. Type STOP to stop the obersavtion task!")
    val input = readlnOrNull()
    if (input == "STOP") {
        observer.cancel()
        println("Observer stopped!")
    }
    println("Do you want to start the process again? (y/n)")
    val input2 = readlnOrNull()
    if (input2 == "y") observer.start(xmlFolder, 10000)
    println("Do you want to stop the process? (y/n)")
    val input3 = readlnOrNull()
    if (input3 == "y") {
        if (observer.isAlive()) observer.cancel()
    }
}
