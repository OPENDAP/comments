package org.opendap.servlet;
/*
 * /////////////////////////////////////////////////////////////////////////////
 * // This file is part of the "Hyrax Data Server" project.
 * //
 * //
 * // Copyright (c) 2019 OPeNDAP, Inc.
 * // Author: Nathan David Potter  <ndp@opendap.org>
 * //
 * // This library is free software; you can redistribute it and/or
 * // modify it under the terms of the GNU Lesser General Public
 * // License as published by the Free Software Foundation; either
 * // version 2.1 of the License, or (at your option) any later version.
 * //
 * // This library is distributed in the hope that it will be useful,
 * // but WITHOUT ANY WARRANTY; without even the implied warranty of
 * // MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * // Lesser General Public License for more details.
 * //
 * // You should have received a copy of the GNU Lesser General Public
 * // License along with this library; if not, write to the Free Software
 * // Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301
 * //
 * // You can contact OPeNDAP, Inc. at PO Box 112, Saunderstown, RI. 02874-0112.
 * /////////////////////////////////////////////////////////////////////////////
 */

import net.sf.saxon.s9api.*;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.input.sax.XMLReaderSAX2Factory;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.jdom2.transform.XSLTransformer;
import org.slf4j.Logger;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.HashMap;

/**
 * User: ndp
 * Date: Apr 23, 2008
 * Time: 9:22:11 AM
 */

class Zerializer extends Serializer {
    public Zerializer(Processor processor) {
        super(processor);
    }
}

public class Transformer {



    private static Logger log;
    static {
        log = org.slf4j.LoggerFactory.getLogger(Transformer.class);
    }

    private Processor proc;
    private Zerializer serializer;
    private XsltTransformer transform;
    private Date cacheTime;
    private String xsltDoc;
    private DocumentBuilder builder;
    private HashMap<QName,XdmValue> currentParameters = new HashMap<>();

    private Transformer(){
        proc = null;
        serializer = null;
        transform = null;
        cacheTime = null;
        xsltDoc = null;
        builder = null;
        currentParameters = new HashMap<>();
    }

    public Transformer(String xsltDocument) throws SaxonApiException {
        this();

        proc = new Processor(false);
        xsltDoc = xsltDocument;
        init(proc, new StreamSource(xsltDoc));

    }

    public Transformer(File xsltDocumentFile) throws SaxonApiException {
        this();

        proc = new Processor(false);
        xsltDoc = xsltDocumentFile.getAbsolutePath();
        init(proc, new StreamSource(xsltDocumentFile));

    }

    public Transformer(Processor proc, String xsltDocument) throws SaxonApiException {
        this();

        xsltDoc = xsltDocument;
        init(proc, new StreamSource(xsltDoc));

    }

    public Transformer(StreamSource xsltDocStream) throws SaxonApiException {
        this();

        proc = new Processor(false);
        init(proc, xsltDocStream);

    }

    private void init(Processor processor,StreamSource xsltDocument) throws SaxonApiException {

        proc = processor;

        // Get an XSLT processor and serializer
        serializer = new Zerializer(processor);
        serializer.setOutputProperty(Serializer.Property.METHOD, "xml");
        serializer.setOutputProperty(Serializer.Property.INDENT, "yes");
        builder = getDocumentBuilder();
        loadTransform(xsltDocument);
    }

    public XdmNode build(java.io.File file ) throws SaxonApiException {
        return builder.build(file);
    }

    public XdmNode build(javax.xml.transform.Source source) throws SaxonApiException {
        return builder.build(source);
    }

    public DocumentBuilder getDocumentBuilder() {
        DocumentBuilder builder = proc.newDocumentBuilder();
        builder.setLineNumbering(true);
        return builder;

    }

    public void reloadTransformIfRequired() throws SaxonApiException {
        if(xsltDoc!=null){
            File f = new File(xsltDoc);
            if(f.lastModified()>cacheTime.getTime()){
                loadTransform(new StreamSource(xsltDoc));
            }
        }

    }

    private void loadTransform(StreamSource xsltDocStream) throws SaxonApiException{
        // Get an XSLT compiler with our transform in it.
        XsltCompiler comp = proc.newXsltCompiler();
        XsltExecutable exp = comp.compile(xsltDocStream);
        transform = exp.load(); // loads the transform file.
        cacheTime = new Date();

    }

    public Processor getProcessor(){
        return proc;
    }

    public void setProcessor(Processor processor){
        proc = processor;
    }

    public Serializer getSerializer() {
        return serializer;
    }

    public void setSource(Source s) throws SaxonApiException {
        transform.setSource(s);
    }


    public void setOutputStream(OutputStream os){
        serializer.setOutputStream(os);
        transform.setDestination(serializer);
    }


    public Destination getDestination(){
        return transform.getDestination();
    }

    public void setDestination(Destination destination){
        transform.setDestination(destination);
    }

    public void setDestination(Transformer destination){
        transform.setDestination(destination.getCurrentTransform());
    }

    public static ByteArrayInputStream transform(Source inputDocumentSource, StreamSource transformDocumentSource) throws SaxonApiException {
        Transformer t = new Transformer(transformDocumentSource);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        t.transform(inputDocumentSource,baos);
        return new ByteArrayInputStream(baos.toByteArray());
    }


    public static Document getTransformedDocument(Source inputDocumentSource, StreamSource transformDocumentSource) throws SaxonApiException, IOException, JDOMException {

        Transformer t = new Transformer(transformDocumentSource);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        t.transform(inputDocumentSource,baos);
        SAXBuilder sb =  new SAXBuilder();
        Document result = sb.build(new ByteArrayInputStream(baos.toByteArray()));
        log.debug("Transform complete.");
        return result;

    }

    public XPathCompiler newXPathCompiler(){
        return proc.newXPathCompiler();
    }

    public void transform(XdmNode doc, OutputStream os) throws SaxonApiException {
        serializer.setOutputStream(os);
        transform.setInitialContextNode(doc);
        transform.setDestination(serializer);
        transform.transform();
    }

    public void transform(Source s, OutputStream os) throws SaxonApiException {
        serializer.setOutputStream(os);
        transform.setSource(s);
        transform.setDestination(serializer);
        transform.transform();
    }

    public void transform() throws SaxonApiException {
        transform.transform();
    }

    public void transform(Source s) throws SaxonApiException {
        transform.setSource(s);
        transform.transform();
    }

    public InputStream  transform(String inputDocumentUrl) throws SaxonApiException {
        StreamSource s = new StreamSource(inputDocumentUrl);
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        serializer.setOutputStream(os);
        transform.setSource(s);
        transform.setDestination(serializer);
        transform.transform();
        ByteArrayInputStream bis = new ByteArrayInputStream(os.toByteArray());

        log.debug("Transformed document is "+os.size()+" bytes.");
        return bis;
    }

    public Document getTransformedDocument(Source inputDocumentSource) throws SaxonApiException, IOException, JDOMException {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        log.debug("getTransformedDocument() - Transforming...");

        transform(inputDocumentSource, baos);
        log.debug("getTransformedDocument() - Transform completed.");

        SAXBuilder sb =  new SAXBuilder();
        Document result = sb.build(new ByteArrayInputStream(baos.toByteArray()));
        log.debug("getTransformedDocument() - Docuemtn built.");
        return result;
    }


    public void setParameter(QName name,
                             XdmValue value){

        currentParameters.put(name,value);
        transform.setParameter(name,value);
    }

    public void clearParameter(String name) throws SaxonApiException {
        QName qname = new QName(name);
        setParameter(qname, null);
        currentParameters.remove(qname);

    }

    public void clearAllParameters() {
        QName qname;
        for (QName qName : currentParameters.keySet()) {
            qname = qName;
            setParameter(qname, null);
        }
        currentParameters.clear();

    }

    public void setParameter(Element param) throws SaxonApiException {
        Document doc = new Document();
        doc.setRootElement(param);
        XMLOutputter xmlo = new XMLOutputter(Format.getPrettyFormat());
        ByteArrayInputStream reader = new ByteArrayInputStream(xmlo.outputString(doc).getBytes(ServiceStringEncoding.getCharset()));
        log.debug("Serialized Parameter: \n{}\n",reader.toString());
        XdmNode valueNode = build(new StreamSource(reader));
        setParameter(new QName(param.getName()), valueNode);
    }

    public void setParameter(String name, String value) throws SaxonApiException {
        // Build the remoteHost parameter to pass into the XSLT
        String nodeString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";
        nodeString += "<"+name+">" + value + "</"+name+">";
        ByteArrayInputStream reader = new ByteArrayInputStream(nodeString.getBytes(ServiceStringEncoding.getCharset()));
        XdmNode valueNode = build(new StreamSource(reader));
        // Pass the remoteHost parameter
        setParameter(new QName(name), valueNode);

    }

    public void setParameter(String name, XdmNode value) throws SaxonApiException {
        setParameter(new QName(name), value);
    }


    public void setImportXMLParameter(String name, String importFile) throws SaxonApiException {
        // Build the remoteHost parameter to pass into the XSLT
        String nodeString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";
        nodeString += "<"+name+" xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\" >" +
                "<xsl:import href=\""+ importFile +"\" />" + "SMOOTCHYSMOOTCHYWOO"+
                "</"+name+">";
        ByteArrayInputStream reader = new ByteArrayInputStream(nodeString.getBytes(ServiceStringEncoding.getCharset()));
        XdmNode valueNode = build(new StreamSource(reader));
        // Pass the remoteHost parameter
        setParameter(new QName(name), valueNode);

    }



    public static void jdomXsltTransform(String srcDocUri, String xslDocUri, OutputStream os) throws Exception {
        XMLOutputter xmlo = new XMLOutputter(Format.getPrettyFormat());
        Document sourceDoc, xsltDoc;
        log.debug("jdomXsltTransform() - Loading source document "+srcDocUri);
        sourceDoc = getXMLDoc(srcDocUri);
        if(sourceDoc==null){
            String msg = "FAILED to get source document! srcDocUri: "+srcDocUri;
            log.error("jdomXsltTransform() - {}",msg);
            throw new IOException(msg);

        }
        log.debug("jdomXsltTransform() - Got and parsed XML document: "+srcDocUri);
        log.debug(xmlo.outputString(sourceDoc));

        log.debug("jdomXsltTransform() - Loading transform document "+srcDocUri);
        xsltDoc = getXMLDoc(xslDocUri);

        log.debug("jdomXsltTransform() - Got and parsed XSL document: "+xslDocUri);
        log.debug(xmlo.outputString(xsltDoc));

        log.debug("jdomXsltTransform() - Applying transform...");
        XSLTransformer transformer = new XSLTransformer(xsltDoc);

        log.debug("jdomXsltTransform() - Transformer is an instance of  "+transformer.getClass().getName());

        Document result = transformer.transform(sourceDoc);
        if(result==null){
            String msg = "FAILED to get result document from transform! srcDocUri: "+srcDocUri+" xsltDocUri: "+xslDocUri;
            log.error("jdomXsltTransform() - {}",msg);
            throw new IOException(msg);
        }
        xmlo.output(result, os);
        log.debug(xmlo.outputString(result));
    }

    public XsltTransformer  getCurrentTransform(){
        return transform;
    }

    public static Document getXMLDoc(String s) throws JDOMException, IOException {



        // get a validating jdom parser to parse and validate the XML document.
        // XMLReaderSAX2Factory sax2Factory = new XMLReaderSAX2Factory(false, "org.apache.xerces.parsers.SAXParser");
        SAXBuilder parser = new SAXBuilder();

        parser.setFeature("http://apache.org/xml/features/validation/schema", false);
        Document doc;
        if(s.startsWith("http://") || s.startsWith("https://")){
            log.debug("Appears to be a URL: "+s);
            doc = parser.build(s);
        }
        else {
            File file = new File(s);
            if(!file.exists()){
                throw new IOException("Cannot find file: "+ s);
            }
            if(!file.canRead()){
                throw new IOException("Cannot read file: "+ s);
            }
            FileInputStream fis = null;
            try {
                fis = new FileInputStream(file);
                doc = parser.build(fis);
            }
            finally {
                if(fis!=null){
                    fis.close();
                }
            }
        }
        return doc;
    }

    private static void saxonXsltTransform(String srcDocUri, String xslTransformUri, OutputStream os) throws IOException, SaxonApiException {

        log.debug("Performing transform using Saxon");
        Processor proc = new Processor(false);
        XsltTransformer trans = Transformer.getXsltTransformer(proc, xslTransformUri);
        if(trans==null){
            String msg = "FAILED to get XsltTransformer instance! srcDocUri: "+srcDocUri+" xslTransformUri: "+xslTransformUri;
            log.error("saxonXsltTransform() - {}",msg);
            throw new IOException(msg);

        }
        XdmNode source = Transformer.getXdmNode(proc,srcDocUri);

        Serializer out = new Zerializer(proc);
        out.setOutputProperty(Serializer.Property.METHOD, "xml");
        out.setOutputProperty(Serializer.Property.INDENT, "yes");
        out.setOutputStream(os);

        trans.setInitialContextNode(source);
        trans.setDestination(out);
        trans.transform();

        os.write(0x0a);
        log.debug("Output written to: "+os);

    }


    public static XdmNode getElementAsXdmNode(Processor proc, Element src)
            throws IOException, SaxonApiException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        XMLOutputter xmlo = new XMLOutputter(Format.getCompactFormat());
        xmlo.output(src,baos);
        return proc.newDocumentBuilder().build(new StreamSource(new ByteArrayInputStream(baos.toByteArray())));

    }



    public static XdmNode getXdmNode(Processor proc, String srcDocUri)
            throws IOException, SaxonApiException {
        XdmNode source;
        if(srcDocUri.startsWith("http://")){
            log.debug("Appears to be a URL: "+srcDocUri);
            getRempoteResourceAsString(srcDocUri);
            InputStream is = null;
            try {
                is = getRempoteResourceAsStream(srcDocUri);
                source = proc.newDocumentBuilder().build(new StreamSource(is));
                return source;
            }
            finally {
                if(is!=null)
                    is.close();
            }
        }
        else {
            File file = new File(srcDocUri);
            if(!file.exists()){
                throw new IOException("Cannot find file: "+ srcDocUri);
            }
            if(!file.canRead()){
                throw new IOException("Cannot read file: "+ srcDocUri);
            }
            source = proc.newDocumentBuilder().build(new StreamSource(file));
            return source;
        }
    }


    private static XsltTransformer getXsltTransformer(Processor proc, String xslTransformUri)
            throws IOException, SaxonApiException {

        XsltCompiler comp = proc.newXsltCompiler();
        XsltExecutable exp;
        XsltTransformer trans;
        if(xslTransformUri.startsWith("http://")){
            log.debug("Appears to be a URL: "+xslTransformUri);
            InputStream is = null;
            try {
                is = getRempoteResourceAsStream(xslTransformUri);
                exp = comp.compile(new StreamSource(is));
                trans = exp.load();
                return trans;
            }
            finally {
                if(is!=null)
                    is.close();
            }
        }
        else {
            File file = new File(xslTransformUri);
            if(!file.exists()){
                throw new IOException("Cannot find file: "+ xslTransformUri);
            }
            if(!file.canRead()){
                throw new IOException("Cannot read file: "+ xslTransformUri);
            }
            exp = comp.compile(new StreamSource(file));
            trans = exp.load();
            return trans;
        }
    }


    public static String getRempoteResourceAsString(String url) throws IOException {
        InputStream is = null;
        try {
            is = getRempoteResourceAsStream(url);
            BufferedReader in = new BufferedReader(new InputStreamReader(is));
            StringBuilder response = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            return response.toString();
        }
        finally {
            if(is!=null)
                is.close();
        }
    }



    public static InputStream getRempoteResourceAsStream(String url) throws IOException {
        URL website = new URL(url);
        URLConnection connection = website.openConnection();
        return (connection.getInputStream());
    }





    public static void printUsage(PrintStream ps) {
        ps.println("\n");
        ps.println("Usage:");
        ps.println("    "+Transformer.class.getName()+"   SourceXmlURI  XSLTransformURI");
        ps.println("\n");
    }

    public static void main(String args[]) {
        try {
            if (args.length != 2) {
                Transformer.printUsage(System.err);
                System.exit(-1);
            }
            jdomXsltTransform(args[0], args[1], System.out);
            saxonXsltTransform(args[0], args[1], System.out);

        } catch (Exception e) {
            e.printStackTrace(System.err);

        }
    }


}
