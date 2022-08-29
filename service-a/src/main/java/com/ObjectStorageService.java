package com;

import com.oracle.bmc.ConfigFileReader;
import com.oracle.bmc.Region;
import com.oracle.bmc.auth.AuthenticationDetailsProvider;
import com.oracle.bmc.auth.BasicAuthenticationDetailsProvider;
import com.oracle.bmc.auth.ConfigFileAuthenticationDetailsProvider;
import com.oracle.bmc.auth.InstancePrincipalsAuthenticationDetailsProvider;
import com.oracle.bmc.objectstorage.ObjectStorage;
import com.oracle.bmc.objectstorage.ObjectStorageClient;
import com.oracle.bmc.objectstorage.model.CreateBucketDetails;
import com.oracle.bmc.objectstorage.requests.*;
import com.oracle.bmc.objectstorage.responses.GetBucketResponse;
import com.oracle.bmc.objectstorage.responses.GetNamespaceResponse;
import com.oracle.bmc.objectstorage.responses.ListObjectsResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

/**
 * Example to get a bucket and its statistics.
 * <p>
 * This example first creates a bucket in the compartment corresponding to the compartment OCID passed as the first
 * argument to this program. The name of the bucket created is same as the second argument to the program. It also
 * creates an object in this bucket whose name is the third argument.
 * </p>
 * It then illustrates how we can get a bucket and its statistics (Estimated Size and Estimated Count).
 *
 *
 *   Arguments to provide to the example. The following arguments are expected:
 *             <ul>
 *             <li>The first argument is the OCID of the compartment.</li>  ocid1.compartment.oc1..aaaaaaaaanqrq3bm7tausbvfnpyn2wlfjckmy6b6o4k5x7ukytm3apygokda
 *             <li>The second is the name of bucket to create and later fetch</li>
 *             <li>The third is the name of object to create inside bucket</li>
 *             </ul>
 *
 *             ocid1.tenancy.oc1..aaaaaaaaplmyij3gtnlpnmch43oybz3nxzncmgt5rykaqpqcrx6pads3o2ba smc-bucket smc-obj
 */


public class ObjectStorageService {

    // public final static String UTF16 = "UTF-8";

    static final Logger logger = LoggerFactory.getLogger("LLL");


    public static void main(String[] args) throws Exception {
    /*    String configurationFilePath = "~/.oci/config";
        String profile = "DEFAULT";

        if (args.length != 3) {
            //      throw new IllegalArgumentException(
            //               "Unexpected number of arguments received. Consult the script header comments for expected arguments");
        }

        // final String compartmentId = "ocid1.tenancy.oc1..aaaaaaaaplmyij3gtnlpnmch43oybz3nxzncmgt5rykaqpqcrx6pads3o2ba";//args[0];
        final String bucket = "smc-bucket";// args[1];
        final String object = "f1/o2.txt";//args[2];

        BasicAuthenticationDetailsProvider provider = null;
        // Configuring the AuthenticationDetailsProvider. It's assuming there is a default OCI config file
        // "~/.oci/config", and a profile in that config with the name "DEFAULT". Make changes to the following
        // line if needed and use ConfigFileReader.parse(CONFIG_LOCATION, CONFIG_PROFILE);

       try{
           ConfigFileReader.ConfigFile configFile = ConfigFileReader.parseDefault();


           provider = new ConfigFileAuthenticationDetailsProvider(configFile);
       } catch (Exception e){
           e.printStackTrace();
       }
         if(null == provider) {
            provider = InstancePrincipalsAuthenticationDetailsProvider.builder().build(); //<--- Instance principal
        }

        ObjectStorage client = new ObjectStorageClient(provider);
        client.setRegion(Region.US_ASHBURN_1);

        System.out.println("Getting the namespace.");
        GetNamespaceResponse namespaceResponse =
                client.getNamespace(GetNamespaceRequest.builder().build());
        String namespaceName = namespaceResponse.getValue();
        System.out.println("Namespace. "+ namespaceName);

        listObjects(client,  namespaceName, bucket,"f1/");
        getObjectSaveAsFile(client,  namespaceName, bucket,object);

     */
        connect();
    }

    public static String connect() throws Exception {
        StringBuilder output = new StringBuilder()  ;
        String configurationFilePath = "~/.oci/config";
        String profile = "DEFAULT";

        // final String compartmentId = "ocid1.tenancy.oc1..aaaaaaaaplmyij3gtnlpnmch43oybz3nxzncmgt5rykaqpqcrx6pads3o2ba";//args[0];
        //final String bucket = "smc163";// args[1];
        final String bucket = "smc-bucket";
        final String object = "f1/o2.txt";//args[2];

        BasicAuthenticationDetailsProvider provider = null;
        // Configuring the AuthenticationDetailsProvider. It's assuming there is a default OCI config file
        // "~/.oci/config", and a profile in that config with the name "DEFAULT". Make changes to the following
        // line if needed and use ConfigFileReader.parse(CONFIG_LOCATION, CONFIG_PROFILE);

        try{
            final ConfigFileReader.ConfigFile configFile = ConfigFileReader.parseDefault();
            provider =    new ConfigFileAuthenticationDetailsProvider(configFile);
        }catch (Exception e){

        }


        if(null == provider) {
            provider = InstancePrincipalsAuthenticationDetailsProvider.builder().build(); //<--- Instance principal
        }

        ObjectStorage client = new ObjectStorageClient(provider);
        //client.setRegion(Region.US_ASHBURN_1);

        System.out.println("Getting the namespace.");
        GetNamespaceResponse namespaceResponse =
                client.getNamespace(GetNamespaceRequest.builder().build());
        String namespaceName = namespaceResponse.getValue();
        System.out.println("Namespace. "+ namespaceName);
        output.append("Namespace. "+ namespaceName);
        ListObjectsResponse objects  = listObjects(client,  namespaceName, bucket,"f1/");
        output.append(objects.getListObjects().getObjects());

        getObjectSaveAsFile(client,  namespaceName, bucket,object);
        return output.toString();

    }

    static ListObjectsResponse listObjects(ObjectStorage osClient,
                                           String discoveredNamespace, String discoveredBucket, String discoveredObject) throws IOException {
//        Pattern p = Pattern.compile("^/n/(.+)/b/(.+)/o/(.+)$");
//        Matcher m = p.matcher(ociObjectPath);
//        m.find();
//        = m.group(1);
//        String discoveredBucket = m.group(2);
//        String discoveredObject = m.group(3);

        // Get the object
        // https://docs.oracle.com/en-us/iaas/api/#/en/objectstorage/20160918/Object/GetObject
        var listObjectsRequest = ListObjectsRequest.builder()
                .namespaceName(discoveredNamespace)
                .bucketName(discoveredBucket)
                .prefix(discoveredObject)
                .limit(1000)
                //.objectName(discoveredObject)
                .build();

        var getObjectResponse = osClient.listObjects(listObjectsRequest);
        var getObjectResponseCode = getObjectResponse.get__httpStatusCode__();
        if(getObjectResponseCode!=200) {
            logger.error("GetObject failed - HTTP {}", getObjectResponseCode);
            System.exit(1);
        }
        System.out.println( getObjectResponse.getListObjects());
        System.out.println( getObjectResponse.getListObjects().getObjects());
        System.out.println( getObjectResponse.getListObjects().getObjects().toArray());
        return getObjectResponse;



    }

    static String getObjectSaveAsFile(ObjectStorage osClient,
                                    String discoveredNamespace,String discoveredBucket, String discoveredObject) throws IOException {
//        Pattern p = Pattern.compile("^/n/(.+)/b/(.+)/o/(.+)$");
//        Matcher m = p.matcher(ociObjectPath);
//        m.find();
//        = m.group(1);
//        String discoveredBucket = m.group(2);
//        String discoveredObject = m.group(3);

        // Get the object
        // https://docs.oracle.com/en-us/iaas/api/#/en/objectstorage/20160918/Object/GetObject
        var getObjectRequest = GetObjectRequest.builder()
                .namespaceName(discoveredNamespace)
                .bucketName(discoveredBucket)
                .objectName(discoveredObject)
                .build();

        var getObjectResponse = osClient.getObject(getObjectRequest);
        var getObjectResponseCode = getObjectResponse.get__httpStatusCode__();
        if(getObjectResponseCode!=200) {
            logger.error("GetObject failed - HTTP {}", getObjectResponseCode);
            System.exit(1);
        }

        var downloadedFilePath = Paths.get("/tmp/oci-objstore-test-"+discoveredObject.replace('/','-'));
        var isExisting = Files.exists(downloadedFilePath);
        if(isExisting) {
            logger.warn("{} already exists and will be replaced", downloadedFilePath );
        }
        Files.copy(getObjectResponse.getInputStream(), downloadedFilePath, StandardCopyOption.REPLACE_EXISTING);
        logger.info("Successfully saved file locally as {}", downloadedFilePath);
        return downloadedFilePath.toString();
    }

 /*   public static void mainWorkingBackup(String[] args) throws Exception {
        String configurationFilePath = "~/.oci/config";
        String profile = "DEFAULT";

        if (args.length != 3) {
            throw new IllegalArgumentException(
                    "Unexpected number of arguments received. Consult the script header comments for expected arguments");
        }

        final String compartmentId = "ocid1.tenancy.oc1..aaaaaaaaplmyij3gtnlpnmch43oybz3nxzncmgt5rykaqpqcrx6pads3o2ba";//args[0];
        final String bucket = "smcbuck";// args[1];
        final String object = "obj";//args[2];

        // Configuring the AuthenticationDetailsProvider. It's assuming there is a default OCI config file
        // "~/.oci/config", and a profile in that config with the name "DEFAULT". Make changes to the following
        // line if needed and use ConfigFileReader.parse(CONFIG_LOCATION, CONFIG_PROFILE);

        final ConfigFileReader.ConfigFile configFile = ConfigFileReader.parseDefault();

        final AuthenticationDetailsProvider provider =
                new ConfigFileAuthenticationDetailsProvider(configFile);

        ObjectStorage client = new ObjectStorageClient(provider);
        client.setRegion(Region.US_ASHBURN_1);

        System.out.println("Getting the namespace.");
        GetNamespaceResponse namespaceResponse =
                client.getNamespace(GetNamespaceRequest.builder().build());
        String namespaceName = namespaceResponse.getValue();

        System.out.println("Creating the source bucket.");
        CreateBucketDetails createSourceBucketDetails =
                CreateBucketDetails.builder().compartmentId(compartmentId).name(bucket).build();
        CreateBucketRequest createSourceBucketRequest =
                CreateBucketRequest.builder()
                        .namespaceName(namespaceName)
                        .createBucketDetails(createSourceBucketDetails)
                        .build();
        client.createBucket(createSourceBucketRequest);

        System.out.println("Creating the source object");
        PutObjectRequest putObjectRequest =
                PutObjectRequest.builder()
                        .namespaceName(namespaceName)
                        .bucketName(bucket)
                        .objectName(object)
                        .contentLength(4L)
                        .putObjectBody(
                                new ByteArrayInputStream("data".getBytes(StandardCharsets.UTF_8)))
                        .build();
        client.putObject(putObjectRequest);


        getObjectSaveAsFile(client,  namespaceName, bucket,object);


        System.out.println("Creating Get bucket request");
        List<GetBucketRequest.Fields> fieldsList = new ArrayList<>(2);
        fieldsList.add(GetBucketRequest.Fields.ApproximateCount);
        fieldsList.add(GetBucketRequest.Fields.ApproximateSize);
        GetBucketRequest request =
                GetBucketRequest.builder()
                        .namespaceName(namespaceName)
                        .bucketName(bucket)
                        .fields(fieldsList)
                        .build();

        System.out.println("Fetching bucket details");
        GetBucketResponse response = client.getBucket(request);

        System.out.println("Bucket Name : " + response.getBucket().getName());
        System.out.println("Bucket Compartment : " + response.getBucket().getCompartmentId());
        System.out.println(
                "The Approximate total number of objects within this bucket : "
                        + response.getBucket().getApproximateCount());
        System.out.println(
                "The Approximate total size of objects within this bucket : "
                        + response.getBucket().getApproximateSize());
        System.console().readLine();
    } */

}