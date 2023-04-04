import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.document.spec.UpdateItemSpec;
import com.amazonaws.services.dynamodbv2.document.utils.NameMap;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.amazonaws.services.dynamodbv2.model.ReturnValue;
import dio.dynamodb.model.Music;

public class RunDynamo {

    String acessKey = ""; //Insira sua chave pública
    static  String secretKey = ""; //Insira sua chave privada

    static  String regiao = ""; //Insira sua região

    static BasicAWSCredentials credentials = new BasicAWSCredentials (secretKey,secretKey);

    static AWSCredentialsProvider awsCredentialsProvider = new AWSStaticCredentialsProvider(credentials);

    static AmazonDynamoDB client = AmazonDynamoDBClientBuilder
            .standard()
            .withCredentials(awsCredentialsProvider)
            .withRegion(Regions.US_EAST_1)// Substitua por sua região
            .build();
    static DynamoDB dynamoDB = new DynamoDB(client);

    static String tableName = "Musics";
    public static void main(String[] args) {

        createItems();
        retrieveItem();
        updateAddNewAttribute();
        deleteItem();

    }
    private static void createItems() {

        Table table = dynamoDB.getTable(tableName);
        DynamoDBMapper mapper = new DynamoDBMapper(client);

        try {
            Music music = new Music();
            music.setArtist("Iron Maiden 7");
            music.setAlbumTitle("Fear of the Dark Tour");
            music.setSongTitle("Fear of the Dark");
            music.setSongYear("2000");

            Item item = new Item()
                    .withString("artist", music.getArtist())
                    .withString("album_title", music.getAlbumTitle())
                    .withString("song_title", music.getSongTitle())
                    .withString("song_year", music.getSongYear());

            table.putItem(item);

            item = new Item()
                    .withString("artist", "Iron Maiden 1")
                    .withString("album_title", "Wasting Love")
                    .withString("song_title", "Fear of the Dark Live")
                    .withString("song_year", "1992");
            table.putItem(item);

            item = new Item()
                    .withString("artist", "Iron Maiden 2")
                    .withString("album_title", "Weekend Warrior")
                    .withString("song_title", "Fear of the Dark")
                    .withString("song_year", "1992");
            table.putItem(item);

            item = new Item()
                    .withString("artist", "Iron Maiden 3")
                    .withString("album_title", "Fear of the Dark")
                    .withString("song_title", "Fear of the Dark Tour")
                    .withString("song_year", "1992");
            table.putItem(item);

            System.err.println("Create items sucess.");

        }
        catch (Exception e) {
            System.err.println("Create items failed.");
            System.err.println(e.getMessage());

        }
    }

    private static void retrieveItem() {
        Table table = dynamoDB.getTable(tableName);

        try {

            Item item = table.getItem("artist", "Iron Maiden 3", "artist, song_title, album_title, song_year,id", null);


            System.out.println("Printing item after retrieving it....");
            System.out.println(item.toJSONPretty());

        }
        catch (Exception e) {
            System.err.println("GetItem failed.");
            System.err.println(e.getMessage());
        }

    }

    private static void updateAddNewAttribute() {
        Table table = dynamoDB.getTable(tableName);

        try {

            UpdateItemSpec updateItemSpec = new UpdateItemSpec().withPrimaryKey("artist","Iron Maiden 1")
                    .withUpdateExpression("set #na = :val1").withNameMap(new NameMap().with("#na", "id"))
                    .withValueMap(new ValueMap().withInt(":val1", 1).with(":val1", 1)).withReturnValues(ReturnValue.ALL_NEW);


            UpdateItemOutcome outcome = table.updateItem(updateItemSpec);

            // Check the response.
            System.out.println("Printing item after adding new attribute...");
            System.out.println(outcome.getItem().toJSONPretty());

        }
        catch (Exception e) {
            System.err.println("Failed to add new attribute in " + tableName);
            System.err.println(e.getMessage());
        }
    }

    private static void deleteItem() {

        Table table = dynamoDB.getTable(tableName);

        try {

            DeleteItemOutcome outcome = table.deleteItem("artist", "Iron Maiden 3");

            // Check the response.
            System.out.println("Printing item that was deleted...");
            System.out.println(outcome.getItem().toJSONPretty());

        }
        catch (Exception e) {
            System.err.println("Error deleting item in " + tableName);
            System.err.println(e.getMessage());
        }
    }
}


