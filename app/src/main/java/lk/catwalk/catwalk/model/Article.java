package lk.catwalk.catwalk.model;

public class Article {
    private String title;
    private String content;
    private String image;
    private String publishedDate;
    private String author;
    private String source;

    public Article(String title, String content, String image, String publishedDate, String author, String source) {
        this.title = title;
        this.content = content;
        this.image = image;
        this.publishedDate = publishedDate;
        this.author = author;
        this.source = source;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getImage() {
        return image;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public String getAuthor() {
        return author;
    }

    public String getSource() {
        return source;
    }
}
