package StudyWeb.service;

import StudyWeb.domain.*;
import StudyWeb.dto.CommentResponseDto;
import StudyWeb.dto.PostRequestCreateDto;
import StudyWeb.dto.PostRequestUpdateDto;
import StudyWeb.repository.PostResponseDto;
import StudyWeb.exception.PostNotFoundException;
import StudyWeb.exception.UserNotFoundException;
import StudyWeb.exception.UserNotMatchException;
import StudyWeb.repository.PostRepository;
import StudyWeb.repository.UserRepository;
import StudyWeb.status.GroupStatus;
import StudyWeb.status.ResponseStatusDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    //study 생성과 게시판 글 올리기 생성 및 제거 다 포함
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final TagService tagService;



    /*
     * images,tags 엔티티가 생성되지 않은 시점에서 응답으로 필요한 데이터라서 new ArrayList<>로 직접 넣어줘야함
     * 즉,Image 엔티티와 Tag 엔티티가 생성되기 전에 chat 인스턴스에서 접근해서 우선적으로 만드는것
     * likes => list 넣어줄 필요 없음
     * */
    @Transactional
    public PostResponseDto createChat(PostRequestCreateDto requestPostDto) throws IOException {
        User user = userRepository.findByUsername(requestPostDto.getUsername());
        Chat chat = Chat.builder()
                .user(user)
                .title(requestPostDto.getTitle())
                .content(requestPostDto.getContent())
                .hit(0L)
                .build();

        Chat save = postRepository.save(chat);
        log.info("Create Chat {} By {}",save.getTitle(),save.getUser().getUsername());
        user.addPost(save);
        return PostResponseDto.builder()
                .id(save.getId())
                .title(save.getTitle())
                .username(save.getUser().getUsername())
                .build();
    }

    @Transactional
    public PostResponseDto createStudy(PostRequestCreateDto requestPostDto) throws IOException {
        List<PostTag> postTags = new ArrayList<>();
        List<Tag> tags = createTags(requestPostDto, postTags);
        User user = userRepository.findByUsername(requestPostDto.getUsername());
                //.orElseThrow(UserNotFoundException::new);
        StudyGroup group = StudyGroup.builder()
                .user(user)
                .title(requestPostDto.getTitle())
                .content(requestPostDto.getContent())
                .groupStatus(GroupStatus.ACTIVE)
                .hit(0L)
                //.images(new ArrayList<>())
                .tags(postTags)
                .build();
        //createPostImages(requestPostDto,group);
        setPostOnPostTag(postTags,group);
        StudyGroup save = postRepository.save(group);
        log.info("Create Study {} By {}",save.getTitle(),save.getUser().getUsername());
        user.addPost(save);
        return PostResponseDto.builder()
                .id(save.getId())
                .title(save.getTitle())
                //.url(getImageUrl(save))
                .username(save.getUser().getUsername())
                .tags(tags.stream().map(Tag::getName).collect(Collectors.toList()))
                .build();
    }


    private void setPostOnPostTag(List<PostTag> postTags, Post post) {
        for (PostTag postTag : postTags) {
            postTag.changePost(post);
        }
    }

    private List<Tag> createTags(PostRequestCreateDto requestPostDto, List<PostTag> postTags) {
        List<Tag> tags = tagService.findTags(
                requestPostDto.getTags()
                        .stream().map(String::toUpperCase)
                        .collect(Collectors.toList()));
        for (Tag tag : tags) {
            postTags.add(PostTag.builder().tag(tag).build());
        }
        return tags;
    }

    private String getTagNameFromPostTags(PostTag postTag) {
        return tagService.findTagName(postTag);
    }


    public List<Post> findAllPosts(User user) {
        return postRepository.findAllByUserId(user.getId()).orElseThrow(UserNotFoundException::new);
    }

    public Page<PostResponseDto> findAllChats(Pageable pageable, String order, String s) {
        PostSearch postSearch = PostSearch.builder()
                .sentence(s)
                .order(order)
                .build();
        return postRepository.findAllChatsBy(pageable, postSearch).map(
                chat -> PostResponseDto
                        .builder()
                        .id(chat.getId())
                        .title(chat.getTitle())
                        .content(chat.getContent())
                        .username(chat.getUser().getUsername())
                        .commentsSize(chat.getComments().size())
                        .createAt(chat.getCreateAt())
                        .lastModifiedAt(chat.getLastModifiedAt())
                        .build()
        );
    }

    public Page<PostResponseDto> findAllStudies(Pageable pageable,GroupStatus status,String order,List<String> tags,String s) {
        PostSearch postSearch = PostSearch.builder()
                .order(order)
                .sentence(s)
                .tagId(Optional.ofNullable(tags).orElseGet(Collections::emptyList).stream().map(tagService::findTagIdByString).collect(Collectors.toList()))
                .groupStatus(status)
                .build();

        return postRepository.findAllStudiesBy(pageable,postSearch).map(
                study -> PostResponseDto
                        .builder()
                        .id(study.getId())
                        .title(study.getTitle())
                        .content(study.getContent())
                        .username(study.getUser().getUsername())
                        .groupStatus(study.getGroupStatus())
                        .tags(study.getPostTags().stream().map(this::getTagNameFromPostTags).collect(Collectors.toList()))
                        .commentsSize(study.getComments().size())
                        .createAt(study.getCreateAt())
                        .lastModifiedAt(study.getLastModifiedAt())
                        .build()
        );
    }

    @Transactional
    public PostResponseDto findChatById(Long id) {
        log.info("Selected Chat ID : {}",id);
        Chat chat = postRepository.findChatById(id).orElseThrow(PostNotFoundException::new);
        log.info("Selected Chat Title : {}", chat.getTitle());
        chat.plusHit();
        log.info("Current Hit : {}", chat.getHit());
        return PostResponseDto.builder()
                .id(chat.getId())
                .username(chat.getUser().getUsername())
                .content(chat.getContent())
                .title(chat.getTitle())
                .comments(
                        chat.getComments().stream()
                                .sorted(Comparator.comparing(Comment::getGroupNum)
                                        .thenComparing(Comment::getId))
                                .map(comment -> CommentResponseDto.builder()
                                        .username(comment.getUser().getUsername())
                                        .contents(comment.getContents())
                                        .commentId(comment.getId())
                                        .deleted(comment.isDeleted())
                                        .group(comment.getGroupNum())
                                        .parent(comment.getParent())
                                        .lastModifiedAt(comment.getLastModifiedAt())
                                        .createAt(comment.getCreateAt())
                                        .build()
                                )
                                .collect(Collectors.toList())
                )
                .tags(chat.getPostTags().stream().map(this::getTagNameFromPostTags).collect(Collectors.toList()))
                .createAt(chat.getCreateAt())
                .build();
    }

    @Transactional
    public PostResponseDto findStudyById(Long id) {
        log.info("Selected Study ID : {}",id);
        StudyGroup study = postRepository.findStudyById(id).orElseThrow(PostNotFoundException::new);
        log.info("Selected Study Title : {}", study.getTitle());
        study.plusHit();
        log.info("Current Hit : {}", study.getHit());
        return PostResponseDto.builder()
                .id(study.getId())
                .username(study.getUser().getUsername())
                .content(study.getContent())
                .title(study.getTitle())
                .groupStatus(study.getGroupStatus())
                .comments(
                        study.getComments().stream()
                                .sorted(Comparator.comparing(Comment::getGroupNum)
                                        .thenComparing(Comment::getId))
                                .map(comment -> CommentResponseDto.builder()
                                        .username(comment.getUser().getUsername())
                                        .contents(comment.getContents())
                                        .commentId(comment.getId())
                                        .deleted(comment.isDeleted())
                                        .group(comment.getGroupNum())
                                        .parent(comment.getParent())
                                        .createAt(comment.getCreateAt())
                                        .lastModifiedAt(comment.getLastModifiedAt())
                                        .build()
                                ).collect(Collectors.toList())
                )
                .tags(study.getPostTags().stream().map(this::getTagNameFromPostTags).collect(Collectors.toList()))
                .createAt(study.getCreateAt())
                .build();
    }


    @Transactional
    public void updateChat(Long chatId, PostRequestUpdateDto updateDto) throws IOException {
        Chat chat = postRepository.findChatById(chatId).orElseThrow(PostNotFoundException::new);
        chat.updatePost(updateDto);
    }

    @Transactional
    public void updateStudy(Long studyId, PostRequestUpdateDto updateDto) throws IOException {
        StudyGroup study = postRepository.findStudyById(studyId).orElseThrow(PostNotFoundException::new);
        if (!isSameTags(study.getPostTags(), updateDto.getTags().stream().map(String::toUpperCase).collect(Collectors.toList()))) {
            updateTags(updateDto, study);
        }
        study.updatePost(updateDto);
    }



    private void updateTags(PostRequestUpdateDto updateDto, Post post) {
        clearPostTagList(post);
        List<Tag> tags = tagService.findTags(updateDto.getTags().stream().map(String::toUpperCase).collect(Collectors.toList()));
        List<PostTag> collect = tags.stream().map(t -> PostTag.builder().tag(t).post(post).build()).collect(Collectors.toList());
        for (PostTag postTag : collect) {
            post.getPostTags().add(postTag);
        }
    }

    private void clearPostTagList(Post post) {
        post.getPostTags().clear();
    }

    private boolean isSameTags(List<PostTag> postTags,List<String> input) {
        for (PostTag postTag : postTags) {
            String tagName = tagService.findTagName(postTag);
            if (!input.contains(tagName)) {
                return false;
            }
        }
        return true;
    }

    @Transactional
    public void deleteChat(Chat chat) {
        postRepository.delete(chat);
    }


    @Transactional
    public void deleteStudy(StudyGroup study) {
        postRepository.delete(study);
    }

//

    @Transactional
    public ResponseStatusDto updateStudyStatus(Long studyId, String username) {
        StudyGroup study = postRepository.findStudyById(studyId).orElseThrow(PostNotFoundException::new);
        isOwner(study, username);
        if (study.getGroupStatus() == GroupStatus.ACTIVE) {
            study.updateStatus(GroupStatus.CLOSED);
            return ResponseStatusDto.builder()
                    .studyStatus(study.getGroupStatus())
                    .id(study.getId())
                    .build();
        }
        study.updateStatus(GroupStatus.ACTIVE);
        return ResponseStatusDto.builder()
                .studyStatus(study.getGroupStatus())
                .id(study.getId())
                .build();
    }

    private void isOwner(Post post, String username) {
        if (!post.getUser().getUsername().equals(username)) {
            throw new UserNotMatchException();
        }
    }

    public int getAllChatSize() {
        return postRepository.findAllChatsWithoutSorting().size();
    }

    public int getAllStudiesSize() {
        return postRepository.findAllStudiesWithoutSorting().size();
    }


    public List<PostResponseDto> findAllChatsByUser(User user) {
        return postRepository.findAllChatsByUser(user).orElseThrow(PostNotFoundException::new)
                .stream().map(c -> PostResponseDto.builder()
                        .id(c.getId())
                        .commentsSize(c.getComments().size())
                        .title(c.getTitle())
                        .content(c.getContent())
                        .createAt(c.getCreateAt())
                        .lastModifiedAt(c.getLastModifiedAt())
                        .build()
                ).collect(Collectors.toList());
    }

    public List<PostResponseDto> findAllStudiesByUser(User user) {
        return postRepository.findAllStudiesByUser(user).orElseThrow(PostNotFoundException::new)
                .stream().map(s -> PostResponseDto.builder()
                        .id(s.getId())
                        //.hit(s.getHit())
                        //.like(s.getLikes().size())
                        .commentsSize(s.getComments().size())
                        .title(s.getTitle())
                        .content(s.getContent())
                        .createAt(s.getCreateAt())
                        .lastModifiedAt(s.getLastModifiedAt())
                        .tags(s.getPostTags().stream().map(pt -> pt.getTag().getName()).collect(Collectors.toList()))
                        .groupStatus(s.getGroupStatus())
                        .build()
                ).collect(Collectors.toList());
    }
}

