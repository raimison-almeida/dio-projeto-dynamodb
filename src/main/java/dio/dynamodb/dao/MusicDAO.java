package dio.dynamodb.dao;


import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import dio.dynamodb.model.Music;

public class MusicDAO {

     DynamoDBMapper dynamoDBMapper;

    public MusicDAO(){

    }

    public void save(Music music){
        try {
            dynamoDBMapper.save(music);
        }catch (Exception e) {
            System.err.println("Create items failed.");

        }

    }

    public void delete(Music music){
        dynamoDBMapper.delete(music);
    }

    public Music findByArtist(String artist){
        return dynamoDBMapper
                .load(Music.class, artist);
    }
}
