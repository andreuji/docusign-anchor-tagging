package org.example;

import com.docusign.esign.api.EnvelopesApi;
import com.docusign.esign.client.ApiClient;
import com.docusign.esign.client.ApiException;
import com.docusign.esign.client.auth.OAuth;
import com.docusign.esign.model.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            // Set up the DocuSign API client
            String clientId = "cb8f1946-2c3a-4d00-9c98-e430c11345f6"; // Integration Key
            String clientSecret = "31dac1b2-19ae-4701-8afe-444056860f95"; // User ID
            String privateKeyBase64 = "LS0tLS1CRUdJTiBSU0EgUFJJVkFURSBLRVktLS0tLQpNSUlFb3dJQkFBS0NBUUVBc1hwNDNYMjRyN3JjWHg4bmNMV0E5dzNvaEFUR3JZaEFmczI1TlQyMnpac1JkbGZJCmZQUmVBL3M1R1FFeHhFczYvcGp5a2lDKzRiaUZWTVIzYXUvY2VQa1VMcGNtd3BQVzU2UG5mMXFSY0ZmWTBkOHQKdzY2eWY3Rk5xK21ZUno2ZldqeEJYRWZlb0R3dk1pYmNLdTZFaXNtbjgxeGdybDhPbzFjUU9QbjlEZFc5RWQvRwpxK05Ec3dJSVFWcitubVdlRWN6ZitoOHRMN0p0alNDM0dqQjRjaDFjRGo1cFBDejZFdStuaTNCbUhLZ1NOZGxxClpJQ2Vrb1Awc2pNUmRJQmxmeTBPa2JEVWt0Q3QwSFNHWW1pdjYrdGhTcE9GTGJwVzFUNlJ2ZGpPWHlDYjdtcHkKYXhSU2JXVEdiY3JWKzVuaGdtSmJoQklKKzNLZWJuaUpXZzB0UFFJREFRQUJBb0lCQUFIZnEyK1l5N0hjMjBXLwowUE91SzYwV2NqUGh6d2JEeVB6dHVueTlUeWhDVnlZbnpPWXFhMzU2cXB3blJWL3ZkSmV3NnE5ODNBUFgyU3JwCkNVbFJiNUFrbUVCQWVIaGpPSlZQUjJGTUg1V1BKa1NPajFZSGNRZitTYlh3QmVnK3BlZzlSbW90UzZybk5JOG4KRm5uRVczdXJPU2RuRmZnM3RiSmFYa0dUdkhtem1WeUdaemM5d3g0V2JKVE1iR3E0ZlNHU0l4NjFNbDIvTk5xbQprKzFWT0U3TFZuWHFvZ1Q0ZXltZUtIb2xDN0ZodzgvcWZhUmp0WGZmVDhmeUJOSUY0TGVMR2hpbDk5L1EyWW5vCnRKd0E3VnR1NFd5RVpEUUVMSUs4WW52VjdXTmQvQnBZd1FPblRIamQ2eTFPTFJ1WWJTSGpza0cyQzN5QnlwV2QKajJzMDNvRUNnWUVBNXlYdE1zZWIzOFVRKzlsQWZiWkN4UE1VcitlVi96V1l3TW10Z3dpelNYV2MzYlhYZUMvegoxOUFSc2YrRFc3SUdXeGZaQm85MzliZWRLa2xReUJuTzVSbG0rc2RhOFJGZmtEQTlWR1Q0WnJ4MnZTVDhzaWtICkNyZkYzTWFIOGo5MmJ0SlFrcDR1N1BwRmhkRHF5Vi9iTkhMUVdOY0FqTkVIbVVZQzFDdWkza0VDZ1lFQXhJOVoKeXBqK0x2YjBQZkRRcndkbURxTGt4cEhyVCtPd1RMcDcrd0xSM0VsN0xxcEkyUDhwZ2ZxL1NkU2ZKSWNzaWpUdwpyajdlNXhobStYTzVrQXVEMUU0RFZYdW1paFZrN0RXdGNHZW1uYUlRK3ZMYmFmbEdxQmVxRHB2R1hKYVhaRG84CkU0WGpuRjZxSzJjMHhmeVkyaWhQL0hVV1g3SGh0OG9ZS1FDVHgvMENnWUJvMXZYdjYwTlN5bEk2dUw0Y2hyZGcKRnhUUitBczdsdmF5c29iRmdRTzRsT1RTVnJEbExYSys5dnVmclZIS0hFUHZ1ekJQWG5FOWZNdUhLM3U5VnA5VgpvQ0RxSzlPZXRZK1BtYUduanFod0NKa2VqeEVpU2FPTkErOTFoSUtTcUtBeUFhaFQ1L1ZHdlBrZGliV0VJcnNNCjNlcUw4UENwN2dFeFR1VG5rT2N0UVFLQmdIVi9XNldJSHluajVjekJEMzRSWmlpSnAwZ3NJZ1gzallQUVZoeU8KYXZUTDJCN2xHL2JiVU95cC8zOGE0cWJyUnV5M0NNaWdQSTdZUDNZT2YzWmo2OElWZ0ZpeE9iU0JaWHJ0KzJlZQpEak5jbGRudElzd09mdllzYnpPY1dzOHVGZ0dMN2kyZGM1b2QzSXRsZmw1N3lNTmdKYkE4TGN1MmdiOWp4MTE3ClF0dmhBb0dCQU5hUlBCOE9nZUJkWUYwcmdyNm1OQUtPT2VvM1NSb1U2akFqaVVaRzk4T0g2S25WYXZXbllCWVkKUmlYdkhVZmdNZGZrSEFJZUtaVFUwUWtCc1ZhZ25CTmFZLzNkQnBnMGlZTVcwcXh2cWFiWkQ2TTNrQk9jazM0UwpnU1lUelhkUVhoNmxRU2N2TUNlSVlURW9KSllDQmptSlVoSnlKZ2NrVnJhUEhXYURNZ3dDCi0tLS0tRU5EIFJTQSBQUklWQVRFIEtFWS0tLS0t";
            ArrayList<String> scopes = new ArrayList<String>();
            scopes.add("signature");
            scopes.add("impersonation");
            byte[] privateKeyBytes = Base64.getDecoder().decode(privateKeyBase64.getBytes());
            ApiClient apiClient = new ApiClient();
            apiClient.setOAuthBasePath("account-d.docusign.com");
            apiClient.setBasePath("https://demo.docusign.net/restapi");
            OAuth.OAuthToken oAuthToken;
            oAuthToken = apiClient.requestJWTUserToken(clientId, clientSecret, scopes, privateKeyBytes, 3600);
            apiClient.setAccessToken(oAuthToken.getAccessToken(), oAuthToken.getExpiresIn());

            EnvelopeDefinition envelope = new EnvelopeDefinition();
            envelope.setEmailSubject("Please sign this document");
            envelope.setEmailBlurb("This is a proof of concept document for DocuSign JDK anchor tagging");
            Document doc = new Document();
            File file = new File("D://temp//Termo_de_Cessoo.pdf");

            byte[] fileBytes = Files.readAllBytes(file.toPath());
            doc.setDocumentBase64(Base64.getEncoder().encodeToString(fileBytes));
            doc.setName("Test Document");
            doc.setFileExtension("pdf");
            doc.setDocumentId("1");
            ArrayList<Document> docs = new ArrayList<>();
            docs.add(doc);
            envelope.setDocuments(docs);
            Signer signer = new Signer();
            signer.setEmail("andre.uji@taking.com.br");
            signer.setName("Andre Taking");
            signer.setRecipientId("1");
            signer.setRoutingOrder("1");
            Signer signer2 = new Signer();
            signer2.setEmail("andreuji@gmail.com");
            signer2.setName("Andre Gmail");
            signer2.setRecipientId("2");
            signer2.setRoutingOrder("2");
            Tabs tabs = new Tabs();
            List<SignHere> signHereAnchors = new ArrayList<SignHere>();
            SignHere signHereAnchor = new SignHere();
            signHereAnchor.setAnchorString("Assinante 1");
            signHereAnchor.setAnchorUnits("pixels");
            signHereAnchor.setAnchorXOffset("0");
            signHereAnchor.setAnchorYOffset("0");
            Tabs tabs2 = new Tabs();
            List<SignHere> signHereAnchors2 = new ArrayList<SignHere>();
            SignHere signHereAnchor2 = new SignHere();
            signHereAnchor2.setAnchorString("Assinante 2");
            signHereAnchor2.setAnchorUnits("pixels");
            signHereAnchor2.setAnchorXOffset("0");
            signHereAnchor2.setAnchorYOffset("0");
            signHereAnchors.add(signHereAnchor);
            signHereAnchors2.add(signHereAnchor2);
            tabs.setSignHereTabs(signHereAnchors);
            tabs2.setSignHereTabs(signHereAnchors2);
            signer.setTabs(tabs);
            signer2.setTabs(tabs2);
            envelope.setRecipients(new Recipients());
            ArrayList<Signer> signers = new ArrayList<>();
            signers.add(signer);
            signers.add(signer2);
            envelope.getRecipients().setSigners(signers);
            envelope.setStatus("sent");

            // Send the envelope
            EnvelopesApi envelopesApi = new EnvelopesApi(apiClient);
            EnvelopeSummary envelopeSummary = envelopesApi.createEnvelope("821f0724-7d61-419a-acd2-992b6f48d423", envelope);
            System.out.println("Envelope sent with ID: " + envelopeSummary.getEnvelopeId());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ApiException e) {
            throw new RuntimeException(e);
        }
    }
}