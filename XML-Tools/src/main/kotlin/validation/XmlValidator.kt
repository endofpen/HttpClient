package org.services.reader.validation

import org.xml.sax.SAXException
import java.io.File
import javax.xml.XMLConstants
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.transform.dom.DOMSource
import javax.xml.validation.SchemaFactory

class XmlValidator(private val xsdFile: File) {

    fun validateXML(xmlFilePath: String): Boolean {
        return try {
            val xmlFile = File(xmlFilePath)

            if (!xmlFile.exists() || !xsdFile.exists()) {
                println("Files do not exists!")
                return false
            }

            val schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI)
            val schema = schemaFactory.newSchema(xsdFile)

            val validator = schema.newValidator()

            val dbFactory = DocumentBuilderFactory.newInstance()
            val dBuilder = dbFactory.newDocumentBuilder()
            val xmlDocument = dBuilder.parse(xmlFile)

            validator.validate(DOMSource(xmlDocument))

            println("XML is valid.")
            true
        } catch (e: SAXException) {
            println("XML is invalid: ${e.message}")
            false
        } catch (e: Exception) {
            println("Error occurred: ${e.message}")
            false
        }
    }
}
