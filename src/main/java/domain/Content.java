package domain;

import java.util.List;

/**
 * @Author: wt
 * @Date: 2019/2/10 17:01
 */
public class Content {
    private String id;
    private String title;
    private String author;
    private String time;
    private String content;
    private List<Comment> listComment;

    public Content() {
    }

    public Content(String id, String title, String author, String time, String content, List<Comment> listComment) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.time = time;
        this.content = content;
        this.listComment = listComment;
    }

    public List<Comment> getListComment() {
        return listComment;
    }

    public void setListComment(List<Comment> listComment) {
        this.listComment = listComment;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
