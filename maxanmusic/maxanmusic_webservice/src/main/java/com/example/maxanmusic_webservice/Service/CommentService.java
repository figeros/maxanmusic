package com.example.maxanmusic_webservice.Service;

import com.example.maxanmusic_webservice.DTO.*;
import com.example.maxanmusic_webservice.Entity.Comment;
import com.example.maxanmusic_webservice.Entity.Track;
import com.example.maxanmusic_webservice.Entity.User;
import com.example.maxanmusic_webservice.Repository.CommentRepository;
import com.example.maxanmusic_webservice.Repository.CommentRepositoryPaged;
import com.example.maxanmusic_webservice.Repository.TrackRepository;
import com.example.maxanmusic_webservice.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final TrackRepository trackRepository;
    private final CommentRepository commentRepository;

    private final UserRepository userRepository;
    private final CommentRepositoryPaged commentRepositoryPaged;

    @Autowired
    private ModelMapper modelMapper;

    public Comment AddCommentToTrack(CommentUploadForm comment_upload, String user) {

        LocalDate mevcutTarih = LocalDate.now();

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String tarih = mevcutTarih.format(dateTimeFormatter);

        User artist = userRepository.findByUsername(user);
        Track track = trackRepository.findByTrackId(comment_upload.getCommentedTrack());
        Comment comment = new Comment(artist,track, comment_upload.getComment(), tarih);
        Comment created = commentRepository.save(comment);
        track.getComments().add(created);
        trackRepository.save(track);
        return created;
    }

    public List<Comment> GetTrackCommentById(Long id, Integer pageNo) {
        Pageable paging = PageRequest.of(pageNo, 7);

        return commentRepositoryPaged.findAllByCommentedTrack_TrackIdOrderByCommentIdDesc(id,paging);
    }

    public Long GetTrackCommentCountById(Long id) {
        return commentRepository.countAllByCommentedTrack_TrackId(id);
    }

    public List<CommentShowForm> commentToCommentShowForm(List<Comment> comments) {

            List<CommentShowForm> commentShowForms = new ArrayList<CommentShowForm>();

            commentShowForms = modelMapper.map(comments,new TypeToken<List<CommentShowForm>>(){}.getType());

            return commentShowForms;
    }

    public CommentShowForm savedCommentToCommentShowForm(Comment comment){
        CommentShowForm  savedComment = new CommentShowForm ();

        savedComment = modelMapper.map(comment, CommentShowForm.class);

        return savedComment;
    }
}
