package nu.studer.teamcity.buildscan.internal.slack;

import nu.studer.teamcity.buildscan.BuildScanReference;
import nu.studer.teamcity.buildscan.BuildScanReferences;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public final class SlackNotificationTestInvocation {

    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            throw new IllegalArgumentException("Webhook URL must be specified.");
        }

        Map<String, String> params = new HashMap<>();
        params.put("system.teamcity.buildConfName", "My Configuration");
        params.put("teamcity.serverUrl", "http://tc.server.org");
        params.put("teamcity.build.id", "23");

        BuildScanReferences buildScanReferences = BuildScanReferences.of(Arrays.asList(
            new BuildScanReference("myId", "http://www.myUrl.org/s/abcde"),
            new BuildScanReference("myOtherId", "http://www.myOtherUrl.org/efghi")));

        SlackPayloadFactory payloadFactory = SlackPayloadFactory.create();
        SlackPayload payload = payloadFactory.from(buildScanReferences, params);

        SlackHttpNotifier slackHttpNotifier = SlackHttpNotifier.forWebhook(new URL(args[0]));
        slackHttpNotifier.notify(payload);
    }

}