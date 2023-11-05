package StudyWeb.repository;


import StudyWeb.dto.CommentResponseDto;
import StudyWeb.status.GroupStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostResponseDto {
    private Long id;
    private String username;
    private String title;
    private String content;
    private List<String> url;
    private GroupStatus groupStatus;
    private int commentsSize;//get All 게시글 시 사용
    private List<CommentResponseDto> comments;
    private List<String> tags;
    private LocalDateTime createAt;
    private LocalDateTime lastModifiedAt;
}