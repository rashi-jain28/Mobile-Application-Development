package example.coolp.inclass07;

import java.io.Serializable;

/**
 * Created by coolp on 2/19/2018.
 */

public class Articles implements Serializable {
    Sources source;
    String author;
    String title;
    String description;
    String url;
    String urlToImage;
    String publishedAt;

    public Articles(){

    }
    public Articles(Sources source, String author, String title, String description, String url, String urlToImage, String publishedAt) {
        this.source = source;
        this.author = author;
        this.title = title;
        this.description = description;
        this.url = url;
        this.urlToImage = urlToImage;
        this.publishedAt = publishedAt;
    }

    public Sources getSource() {
        return source;
    }

    public void setSource(Sources source) {
        this.source = source;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public void setUrlToImage(String urlToImage) {
        this.urlToImage = urlToImage;
    }

    public String getPublishedAt() {
        return publishedAt;
    }



    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }
    @Override
    public String toString() {
        return  getAuthor()+getDescription()+getPublishedAt()+getTitle()+getUrlToImage()+getSource();
    }

}
