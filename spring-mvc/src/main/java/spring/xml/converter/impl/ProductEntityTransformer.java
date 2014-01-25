package spring.xml.converter.impl;

import entities.Product;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

import spring.xml.converter.EntityTransformer;

public class ProductEntityTransformer implements EntityTransformer {

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(Product.class);
    }

    @Override
    public void transform(Document document, Element root, Object object) {
        Product p = (Product) object;

        Element productRoot = document.createElement("product");

        productRoot.setAttribute("id", Long.toString(p.getId()));

        Element nameNode = document.createElement("name");
        Text textNameNode = document.createTextNode(p.getName());

        nameNode.appendChild(textNameNode);
        productRoot.appendChild(nameNode);

        Element descriptionNode = document.createElement("description");
        Text textDescriptionNode = document.createTextNode(p.getDescription());
        descriptionNode.appendChild(textDescriptionNode);
        productRoot.appendChild(descriptionNode);

        root.appendChild(productRoot);
    }
}
