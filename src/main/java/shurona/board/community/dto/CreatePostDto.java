package shurona.board.community.dto;

import jakarta.validation.constraints.Size;

public class CreatePostDto {
    @Size(max = 200)
    private String title;

    @Size(max = 1000)
    private String content;

    public CreatePostDto(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
