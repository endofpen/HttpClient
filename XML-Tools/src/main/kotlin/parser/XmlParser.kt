package org.services.reader.parser

import model.Company
import java.io.File
import javax.xml.bind.JAXBContext
import javax.xml.bind.Unmarshaller

class XmlParser {

    fun parse(xmlFilePath: String): Company {
        val jaxbContext: JAXBContext = JAXBContext.newInstance(Company::class.java)
        val unmarshaller: Unmarshaller = jaxbContext.createUnmarshaller()
        return unmarshaller.unmarshal(File(xmlFilePath)) as Company
    }

}
