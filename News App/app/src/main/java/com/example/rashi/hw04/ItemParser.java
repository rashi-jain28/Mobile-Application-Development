package com.example.rashi.hw04;

import android.content.ClipData;
import android.util.Xml;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by Rashi on 2/24/2018.
 */

public class ItemParser {
    public static class ItemsSAXParser extends DefaultHandler{
        ArrayList<Items> items;
        Items item;
        StringBuilder innerXml;
        // return the instance of the parser
        static public ArrayList<Items> parseItems(InputStream inutStream) throws IOException, SAXException {
            ItemsSAXParser parser = new ItemsSAXParser();
            Xml.parse(inutStream,Xml.Encoding.UTF_8,parser);
            return parser.items;
        }

        @Override
        public void startDocument() throws SAXException {
            super.startDocument();
            this.items=new ArrayList<>();
            this.innerXml= new StringBuilder();
        }

        @Override
        public void endDocument() throws SAXException {
            super.endDocument();
        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            super.startElement(uri, localName, qName, attributes);
            if(localName.equals("item")){
                item= new Items();
            } else if(localName.equals("content")) {
                item.imageURL= attributes.getValue("url");
            }
            else
                innerXml.setLength(0);
            /// no need//
            /*else(localName.equals("media"))  {
            }*/
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            super.endElement(uri, localName, qName);
            //item= new Items();

            if(item!=null) {
                if (localName.equals("title")) {
                    item.title = innerXml.toString();
                } else if (localName.equals("description")) {
                    String desc= innerXml.toString().split("<")[0];
                    item.description = desc;
                } else if (localName.equals("pubDate")) {
                    item.publishedAt = innerXml.toString();
                } else if (localName.equals("guid")) {
                    item.link = innerXml.toString();
                }
                else if(localName.equals("item")){
                    items.add(item);
                }
                innerXml.setLength(0);
            }

        }

        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {
            super.characters(ch, start, length);
            innerXml.append(ch, start, length);
        }
    }
}
