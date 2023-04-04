package dio.dynamodb.model;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.Data;
@Data
@DynamoDBTable(tableName = "Musics")
public class Music {

    @DynamoDBHashKey
    @DynamoDBAttribute(attributeName = "artist")
    private String artist;

    @DynamoDBHashKey
    @DynamoDBAttribute(attributeName = "song_title")
    private String songTitle;

    @DynamoDBHashKey
    @DynamoDBAttribute(attributeName = "album_title")
    private String albumTitle;

    @DynamoDBHashKey
    @DynamoDBAttribute(attributeName = "song_year")
    private String songYear;
}
