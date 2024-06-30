package com.base.auth.mapper;

import com.base.auth.dto.comment.CommentDto;
import com.base.auth.dto.news.NewsAutoCompleteDto;
import com.base.auth.dto.user.UserAutoCompleteDto;
import com.base.auth.form.comment.CreateCommentForm;
import com.base.auth.form.comment.UpdateCommentForm;
import com.base.auth.model.Comment;
import com.base.auth.model.News;
import com.base.auth.model.User;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-06-29T17:43:55+0700",
    comments = "version: 1.3.1.Final, compiler: javac, environment: Java 11.0.22 (Oracle Corporation)"
)
@Component
public class CommentMapperImpl implements CommentMapper {

    @Override
    public CommentDto fromEntityToCommentDto(Comment comment) {
        if ( comment == null ) {
            return null;
        }

        CommentDto commentDto = new CommentDto();

        commentDto.setId( comment.getId() );
        commentDto.setStatus( comment.getStatus() );
        if ( comment.getModifiedDate() != null ) {
            commentDto.setModifiedDate( LocalDateTime.ofInstant( comment.getModifiedDate().toInstant(), ZoneId.of( "UTC" ) ) );
        }
        if ( comment.getCreatedDate() != null ) {
            commentDto.setCreatedDate( LocalDateTime.ofInstant( comment.getCreatedDate().toInstant(), ZoneId.of( "UTC" ) ) );
        }
        commentDto.setNews( newsToNewsAutoCompleteDto( comment.getNews() ) );
        commentDto.setUser( userToUserAutoCompleteDto( comment.getUser() ) );
        commentDto.setContent( comment.getContent() );

        return commentDto;
    }

    @Override
    public List<CommentDto> fromEntityToCommentDtoList(List<Comment> comments) {
        if ( comments == null ) {
            return null;
        }

        List<CommentDto> list = new ArrayList<CommentDto>( comments.size() );
        for ( Comment comment : comments ) {
            list.add( fromEntityToCommentDto( comment ) );
        }

        return list;
    }

    @Override
    public Comment fromCreateCommentFormToEntity(CreateCommentForm createCommentForm) {
        if ( createCommentForm == null ) {
            return null;
        }

        Comment comment = new Comment();

        comment.setNews( createCommentFormToNews( createCommentForm ) );
        comment.setContent( createCommentForm.getContent() );

        return comment;
    }

    @Override
    public void updateCommentFromUpdateCommentForm(UpdateCommentForm updateCommentForm, Comment comment) {
        if ( updateCommentForm == null ) {
            return;
        }

        if ( comment.getNews() == null ) {
            comment.setNews( new News() );
        }
        updateCommentFormToNews( updateCommentForm, comment.getNews() );
        if ( updateCommentForm.getContent() != null ) {
            comment.setContent( updateCommentForm.getContent() );
        }
    }

    protected NewsAutoCompleteDto newsToNewsAutoCompleteDto(News news) {
        if ( news == null ) {
            return null;
        }

        NewsAutoCompleteDto newsAutoCompleteDto = new NewsAutoCompleteDto();

        newsAutoCompleteDto.setId( news.getId() );
        newsAutoCompleteDto.setStatus( news.getStatus() );
        newsAutoCompleteDto.setTitle( news.getTitle() );
        newsAutoCompleteDto.setContent( news.getContent() );
        newsAutoCompleteDto.setAvatar( news.getAvatar() );
        newsAutoCompleteDto.setBanner( news.getBanner() );
        newsAutoCompleteDto.setDescription( news.getDescription() );
        newsAutoCompleteDto.setPinTop( news.getPinTop() );

        return newsAutoCompleteDto;
    }

    protected UserAutoCompleteDto userToUserAutoCompleteDto(User user) {
        if ( user == null ) {
            return null;
        }

        UserAutoCompleteDto userAutoCompleteDto = new UserAutoCompleteDto();

        userAutoCompleteDto.setId( user.getId() );

        return userAutoCompleteDto;
    }

    protected News createCommentFormToNews(CreateCommentForm createCommentForm) {
        if ( createCommentForm == null ) {
            return null;
        }

        News news = new News();

        news.setId( createCommentForm.getNewsId() );

        return news;
    }

    protected void updateCommentFormToNews(UpdateCommentForm updateCommentForm, News mappingTarget) {
        if ( updateCommentForm == null ) {
            return;
        }

        if ( updateCommentForm.getNewsId() != null ) {
            mappingTarget.setId( updateCommentForm.getNewsId() );
        }
    }
}
