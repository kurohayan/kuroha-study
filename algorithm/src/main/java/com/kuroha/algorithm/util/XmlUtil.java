package com.kuroha.algorithm.util;

import com.alibaba.fastjson.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author kuroha
 */
public class XmlUtil {

    /**
     * XML格式字符串转换为Map
     *
     * @param strXML XML字符串
     * @return XML数据转换后的Map
     * @throws Exception
     */
    public static Map<String, String> xmlToMap(String strXML) throws Exception {
        try {
            Map<String, String> data = new HashMap<>(1);
            DocumentBuilder documentBuilder = DocumentUtil.newDocumentBuilder();
            InputStream stream = new ByteArrayInputStream(strXML.getBytes("UTF-8"));
            Document doc = documentBuilder.parse(stream);
            doc.getDocumentElement().normalize();
            NodeList nodeList = doc.getDocumentElement().getChildNodes();
            for (int idx = 0; idx < nodeList.getLength(); ++idx) {
                Node node = nodeList.item(idx);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    data.put(element.getNodeName(), element.getTextContent());
                }
            }
            try {
                stream.close();
            } catch (Exception ex) {
                // do nothing
            }
            return data;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }

    }
    /**
     * XML格式字符串转换为对象
     *
     * @param strXML XML字符串
     * @return XML数据转换后的Map
     * @throws Exception
     */
    public static<T> T xmlToObject(String strXML,Class<T> clazz) throws Exception {
        try {
            Map<String, String> data = xmlToMap(strXML);
            return mapToObject(data,clazz);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }

    }
    /**
     * XML格式字符串转换为对象
     *
     * @param data Map类型数据
     * @return XML数据转换后的Map
     * @throws Exception
     */
    public static<T> T mapToObject(Map<String, String> data,Class<T> clazz) throws Exception {
        try {
            Field[] fields = clazz.getDeclaredFields();
            T object = clazz.newInstance();
            for (Field field : fields) {
                field.setAccessible(true);
                String name = StringUtil.firstCharToUpperCase(field.getName());
                String value = data.get(name);
                if (StringUtil.isNotBlank(value)) {
                    field.set(object,value);
                }
            }
            return object;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }

    }

    /**
     * 将Map转换为XML格式的字符串
     *
     * @param data Map类型数据
     * @return XML格式的字符串
     * @throws Exception
     */
    public static String mapToXml(Map<String, String> data) throws Exception {
        Document document = DocumentUtil.newDocument();
        Element root = document.createElement("xml");
        document.appendChild(root);
        for (String key: data.keySet()) {
            String value = data.get(key);
            if (value == null) {
                value = "";
            }
            value = value.trim();
            Element filed = document.createElement(key);
            filed.appendChild(document.createTextNode(value));
            root.appendChild(filed);
        }
        String xml = parseDocumentToString(document);
        xml = xml.replaceAll("--=","<").replaceAll("=--",">").replaceAll("-==","</");
        return xml;
    }
    /**
     * 将Map转换为XML格式的字符串
     *
     * @param data Map类型数据
     * @return XML格式的字符串
     * @throws Exception
     */
    public static String mapToXmlTag(Map<String, String> data) throws Exception {
        Set<Map.Entry<String, String>> entries = data.entrySet();
        StringBuilder xmlTag = new StringBuilder();
        for (Map.Entry<String, String> entry : entries) {
            String key = entry.getKey();
            String value = entry.getValue();
            xmlTag.append("--=").append(key).append("=--").append(value).append("-==").append(key).append("=--");
        }
        return xmlTag.toString();
    }

    /**
     * 将Map转换为XML格式的字符串
     *
     * @param object 对象类型数据
     * @return XML格式的字符串
     * @throws Exception
     */
    public static String objectToXml(Object object) throws Exception {
        Document document = DocumentUtil.newDocument();
        Element root = document.createElement("xml");
        document.appendChild(root);
        Class<?> clazz = object.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            String key = StringUtil.firstCharToUpperCase(field.getName());
            Object o = field.get(object);
            if (o == null) {
                continue;
            }
            String value = String.valueOf(o);
            if (StringUtil.isBlank(value)) {
                continue;
            }
            value = value.trim();
            Element filed = document.createElement(key);
            filed.appendChild(document.createTextNode(value));
            root.appendChild(filed);
        }
        return parseDocumentToString(document);
    }

    private static String parseDocumentToString(Document document) throws TransformerException {
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();
        DOMSource source = new DOMSource(document);
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        StringWriter writer = new StringWriter();
        StreamResult result = new StreamResult(writer);
        transformer.transform(source, result);
        String output = writer.getBuffer().toString();
        try {
            writer.close();
        }
        catch (Exception ex) {
        }
        return output;
    }

    public static String parseResult(String result) {
        JSONObject jsonObject = JSONObject.parseObject(result);
        Integer errcode = jsonObject.getInteger("errcode");
        if (errcode == 0) {
            return Const.SUCCESS;
        }else {
            return jsonObject.getString("errmsg");
        }
    }

    /**
     * @param xmlDoc xml Document对象
     * @author chunfei Diao
     * @since 2009-03-18 XML解析类
     */
    public static Map<String, String> getXMLText(org.jdom.Document xmlDoc) {

        org.jdom.Element methodResponse = xmlDoc.getRootElement();
        List structList = methodResponse.getChild("params").getChild("param")
                .getChild("value").getChild("struct").getChildren();
        Map<String, String> map = new HashMap<>();
        for (Object aStructList : structList) {
            org.jdom.Element memberElement = (org.jdom.Element) aStructList;
            map.put(memberElement.getChildTextTrim("name"), memberElement
                    .getChildTextTrim("value"));
        }
        return map;
    }

    /**
     * @param arg   文件HASH值,验证码,调用接口方法名
     * @author chunfei Diao
     * @since 2009-03-11 XML生成
     */
    public static org.jdom.Document getXMLDoc(String arg[]) {
        org.jdom.Element methodCall = new org.jdom.Element("methodCall");
        org.jdom.Document xmlDoc = new org.jdom.Document(methodCall);
        org.jdom.Element params = new org.jdom.Element("params");
        org.jdom.Element param = new org.jdom.Element("param");
        for (int i = 2; i < (arg.length); i++)
            param.addContent(new org.jdom.Element("value").addContent(new org.jdom.Element(
                    "string").setText(arg[i])));
        params.addContent(param);
        methodCall.addContent(new org.jdom.Element("methodName").setText(arg[1]));
        methodCall.addContent(params);
        return xmlDoc;
    }
}
