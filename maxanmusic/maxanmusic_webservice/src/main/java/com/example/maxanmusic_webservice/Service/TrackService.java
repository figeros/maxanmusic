package com.example.maxanmusic_webservice.Service;

import com.example.maxanmusic_webservice.DTO.TrackUploadForm;
import com.example.maxanmusic_webservice.DTO.UserProfileDTO;
import com.example.maxanmusic_webservice.Entity.Track;
import com.example.maxanmusic_webservice.Entity.User;
import com.example.maxanmusic_webservice.Repository.TrackRepository;
import com.example.maxanmusic_webservice.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class TrackService {

    private final TrackRepository trackRepository;

    private final UserRepository userRepository;

    private static S3Client s3;

    @Autowired
    private ModelMapper modelMapper;

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

}
