import java.io.File
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.transform.TransformerFactory
import javax.xml.transform.dom.DOMSource
import javax.xml.transform.stream.StreamResult
import kotlin.random.Random

fun main() {
    val directory = "src/main/resources/xml" // Ensure this directory exists or create it programmatically
    val companies = listOf("Tech Solutions Inc.", "Global Innovations Ltd.", "Creative Minds LLC", "Future Vision Corp.", "Bright Ideas Co.", "NextGen Pioneers", "Inspire Enterprises", "Dynamic Synergies", "Quantum Leap LLC", "Visionary Ventures")

    // Ensure the directory exists
    File(directory).mkdir()

    for (i in 1..10) {
        writeXmlFile(directory, "company${i}.xml", companies[i - 1])
    }
}

fun writeXmlFile(directory: String, fileName: String, companyName: String) {
    val docFactory = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument()

    // Root element
    val rootElement = docFactory.createElement("company")
    docFactory.appendChild(rootElement)

    // Name element
    val name = docFactory.createElement("name")
    name.appendChild(docFactory.createTextNode(companyName))
    rootElement.appendChild(name)

    // Address
    val address = docFactory.createElement("address")
    rootElement.appendChild(address)

    val street = docFactory.createElement("street")
    street.appendChild(docFactory.createTextNode("123 Example St"))
    address.appendChild(street)

    val city = docFactory.createElement("city")
    city.appendChild(docFactory.createTextNode("Sample City"))
    address.appendChild(city)

    val postalCode = docFactory.createElement("postalCode")
    postalCode.appendChild(docFactory.createTextNode("123456"))
    address.appendChild(postalCode)

    val country = docFactory.createElement("country")
    country.appendChild(docFactory.createTextNode("Wonderland"))
    address.appendChild(country)

    // Departments
    val departments = docFactory.createElement("departments")
    rootElement.appendChild(departments)

    val department = docFactory.createElement("department")
    departments.appendChild(department)

    val departmentName = docFactory.createElement("name")
    departmentName.appendChild(docFactory.createTextNode("Research and Development"))
    department.appendChild(departmentName)

    val employees = docFactory.createElement("employees")
    department.appendChild(employees)

    for (j in 1..2) {
        val employee = docFactory.createElement("employee")
        employees.appendChild(employee)

        val firstName = docFactory.createElement("firstName")
        firstName.appendChild(docFactory.createTextNode("John${Random.nextInt(100)}"))
        employee.appendChild(firstName)

        val lastName = docFactory.createElement("lastName")
        lastName.appendChild(docFactory.createTextNode("Doe${Random.nextInt(100)}"))
        employee.appendChild(lastName)

        val position = docFactory.createElement("position")
        position.appendChild(docFactory.createTextNode("Developer"))
        employee.appendChild(position)

        val salary = docFactory.createElement("salary")
        salary.appendChild(docFactory.createTextNode("${75000 + Random.nextInt(5000)}.00"))
        employee.appendChild(salary)
    }

    // Write the content into XML file
    val transformer = TransformerFactory.newInstance().newTransformer()
    val source = DOMSource(docFactory)
    val result = StreamResult(File("$directory/$fileName"))

    transformer.transform(source, result)

    println("File saved: $directory/$fileName")
}
