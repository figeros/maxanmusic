package com.example.maxanmusic_webservice.Service;

import com.example.maxanmusic_webservice.DTO.*;
import com.example.maxanmusic_webservice.Entity.Follow;
import com.example.maxanmusic_webservice.Entity.Likes;
import com.example.maxanmusic_webservice.Entity.Track;
import com.example.maxanmusic_webservice.Entity.User;
import com.example.maxanmusic_webservice.Repository.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TrackService {

    private final TrackRepository trackRepository;
    private final TrackRepositoryPaged trackRepositoryPaged;
    private final UserRepository userRepository;
    private final FollowRepository followRepository;
    private static S3Client s3;
    @Autowired
    private ModelMapper modelMapper;

    private final LikesRepository likesRepository;
    private final LikesRepositoryPaged likesRepositoryPaged;

    public List<Track> GetUsersTracksByUserName(String username,Integer pageNo){

        Pageable paging = PageRequest.of(pageNo, 6);

        return trackRepositoryPaged.findAllByArtistUsernameOrderByTrackIdDesc(username,paging);
    }

    public List<Track> GetTracksByKeyword(String keyword,Integer pageNo){

        Pageable paging = PageRequest.of(pageNo, 8);

        return trackRepositoryPaged.findAllByTrackNameContainsOrderByTrackIdDesc(keyword,paging);
    }

    public Long GetUsersTracksCountByUserName(String username){
        return trackRepository.countAllByArtistUsernameOrderByTrackId(username);
    }

    public Long GetSearchedTracksCountByKeyword(String keyword){
        return trackRepository.countAllByTrackNameContains(keyword);
    }

    public Track AddTrack(TrackUploadForm track_upload,String user) {

        LocalDate mevcutTarih = LocalDate.now();

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String tarih = mevcutTarih.format(dateTimeFormatter);

        User artist = userRepository.findByUsername(user);
        Track track = new Track(track_upload.getTrackName(),artist,track_upload.getTrackDesc(),tarih);
        Track created = trackRepository.save(track);

        uploadTrackFile(track_upload.getTrackAudio(),created.getTrackId().toString());
        uploadTrackCover(track_upload.getCover(),created.getTrackId().toString());

        artist.getUserTracks().add(created);
        userRepository.save(artist);

        return created;
    }

    public Void uploadTrackFile(MultipartFile track,String TrackId){
        Region region = Region.EU_CENTRAL_1;

        String bucketName = "maxanmusicbucket";

        s3 =S3Client.builder().region(region).build();

        //File file = new File("src/main/resources/temptrack.mp3");

        File file = null;
        try {
            file = File.createTempFile("temptrack",".mp3");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            track.transferTo(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(bucketName).key("trcks/"+TrackId+".mp3").acl("public-read").build();

        s3.putObject(request, RequestBody.fromFile(file));
        file.delete();
        return null;
    }

    public Void uploadTrackCover(MultipartFile cover,String TrackId){
        Region region = Region.EU_CENTRAL_1;

        String bucketName = "maxanmusicbucket";

        s3 =S3Client.builder().region(region).build();


        File file = null;
        try {
            file = File.createTempFile("tempimg",".png");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            cover.transferTo(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(bucketName).key("imgs/tcvr/"+TrackId+".png").acl("public-read").build();

        s3.putObject(request, RequestBody.fromFile(file));
        file.delete();
        return null;
    }

    public Track GetTrackById(Long id){
        return trackRepository.findByTrackId(id);
    }

    public TrackUploadForm trackToTrackUploadForm(Track track){
        TrackUploadForm trackUploadForm = new TrackUploadForm();

        trackUploadForm = modelMapper.map(track, TrackUploadForm.class);

        return trackUploadForm;
    }

    public List<TrackShowcaseDTO> trackToTrackShowcaseForm(List<Track> tracks) {

        List<TrackShowcaseDTO> trackShowcaseDTOS = new ArrayList<TrackShowcaseDTO>();

        trackShowcaseDTOS = modelMapper.map(tracks,new TypeToken<List<TrackShowcaseDTO>>(){}.getType());

        return trackShowcaseDTOS;
    }

    public SavedTrackDTO trackToSavedTrackDTO(Track track){
        SavedTrackDTO savedtrack = new SavedTrackDTO();

        savedtrack = modelMapper.map(track, SavedTrackDTO.class);

        return savedtrack;
    }

    public List<Track> getFollowedUsersTracks(String user,Integer pageNo) {

        Pageable paging = PageRequest.of(pageNo, 8);

        List<Follow> followitems = followRepository.findAllByUserFollowsUsername(user);

        List<User> followedUsers = new ArrayList<>();

        followitems.forEach(followitem -> {
            followedUsers.add(followitem.getUserFollowed());
        });

        List<Track> tracks = trackRepositoryPaged.findAllByArtistInOrderByTrackIdDesc(followedUsers,paging);

        return tracks;
    }

    public Long GetFollowedUsersTracksCount(String user){

        List<Follow> followitems = followRepository.findAllByUserFollowsUsername(user);

        List<User> followedUsers = new ArrayList<>();

        followitems.forEach(followitem -> {
            followedUsers.add(followitem.getUserFollowed());
        });

        return trackRepository.countAllByArtistIn(followedUsers);
    }

    public List<Track> getLikedTracks(String user,Integer pageNo) {

        Pageable paging = PageRequest.of(pageNo, 8);

        List<Likes> likesitems = likesRepositoryPaged.findAllByUserLikedUsernameOrderByLikeidDesc(user,paging);

        List<Track> likedTracks = new ArrayList<>();

        likesitems.forEach(likesitem -> {
            likedTracks.add(likesitem.getTrackLiked());
        });

        return likedTracks;
    }

    public Long GetLikedTracksCount(String user){
        return likesRepository.countAllByUserLikedUsername(user);
    }

    public boolean DeleteTrack(String user, Long TrackId) {

        User Artist = userRepository.findByUsername(user);
        Track track = trackRepository.findByTrackId(TrackId);

        if(track==null){
            return false;
        }

        if(track.getArtist()!=Artist){
            return false;
        }

        trackRepository.delete(track);

        Region region = Region.EU_CENTRAL_1;

        String bucketName = "maxanmusicbucket";

        s3 =S3Client.builder().region(region).build();

        List<ObjectIdentifier> listObjects = new ArrayList<>();

        String key1 = "imgs/tcvr/"+TrackId+".png";
        String key2 = "trcks/"+TrackId+".mp3";

        listObjects.add(ObjectIdentifier.builder().key(key1).build());
        listObjects.add(ObjectIdentifier.builder().key(key2).build());

        DeleteObjectsRequest request = DeleteObjectsRequest.builder()
                .bucket(bucketName)
                .delete(Delete.builder().objects(listObjects).build())
                .build();



        while(true) {
            DeleteObjectsResponse response = s3.deleteObjects(request);

            if (response.hasDeleted()) {
                break;
            }
        }

        return true;
    }


    public boolean EditTrack(TrackEditForm trackform, String user, Long TrackId) {
        User Artist = userRepository.findByUsername(user);
        Track track = trackRepository.findByTrackId(TrackId);

        if(track==null){
            return false;
        }

        if(track.getArtist()!=Artist){
            return false;
        }

        if(trackform.getTrackName()!=track.getTrackName()){
            track.setTrackName(trackform.getTrackName());
        }

        if(trackform.getTrackDesc()!=track.getTrackDesc()){
            track.setTrackDesc(trackform.getTrackDesc());
        }

        if(trackform.getCover()!=null){
            uploadTrackCover(trackform.getCover(),TrackId.toString());
        }

        trackRepository.save(track);
        return true;
    }
}
