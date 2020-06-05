package com.github.wenhao;

import com.github.wenhao.client.EclipseCheClient;
import com.github.wenhao.response.KeyCloakTokenResponse;
import com.google.common.collect.ImmutableMap;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EclipseCheIntegrationTest {

    @Autowired
    EclipseCheClient eclipseCheClient;

    @Test
    public void should_create_ide() {
        // given

        // when
        final Map<String, String> formParameters = ImmutableMap.<String, String>builder()
                .put("grant_type", "password")
                .put("client_id", "che-public")
                .put("username", "admin")
                .put("password", "admin")
                .build();
        KeyCloakTokenResponse response = eclipseCheClient.getToken(formParameters);

        String devFile = "metadata:\n" +
                "  name: tdd-taxi-seed-java\n" +
                "projects:\n" +
                "  - name: tdd-taxi-seed-java\n" +
                "    source:\n" +
                "      location: 'https://git.topproio.com/keju-exams/tdd-taxi-seed-java.git'\n" +
                "      type: git\n" +
                "      branch: master\n" +
                "attributes:\n" +
                "  persistVolumes: 'false'\n" +
                "components:\n" +
                "  - id: redhat/java11/latest\n" +
                "    type: chePlugin\n" +
                "  - mountSources: true\n" +
                "    memoryLimit: 512Mi\n" +
                "    type: dockerimage\n" +
                "    volumes:\n" +
                "      - name: gradle\n" +
                "        containerPath: /home/gradle/.gradle\n" +
                "    image: 'quay.io/eclipse/che-java11-gradle:7.13.1'\n" +
                "    alias: gradle\n" +
                "    env:\n" +
                "      - value: >-\n" +
                "          -XX:MaxRAMPercentage=50 -XX:+UseParallelGC -XX:MinHeapFreeRatio=10\n" +
                "          -XX:MaxHeapFreeRatio=20 -XX:GCTimeRatio=4\n" +
                "          -XX:AdaptiveSizePolicyWeight=90 -Dsun.zip.disableMemoryMapping=true\n" +
                "          -Xms20m -Djava.security.egd=file:/dev/./urandom\n" +
                "        name: JAVA_OPTS\n" +
                "      - value: /home/gradle/.gradle\n" +
                "        name: GRADLE_USER_HOME\n" +
                "      - value: /home/gradle\n" +
                "        name: HOME\n" +
                "      - value: >-\n" +
                "          -XX:MaxRAMPercentage=50 -XX:+UseParallelGC -XX:MinHeapFreeRatio=10\n" +
                "          -XX:MaxHeapFreeRatio=20 -XX:GCTimeRatio=4\n" +
                "          -XX:AdaptiveSizePolicyWeight=90 -Dsun.zip.disableMemoryMapping=true\n" +
                "          -Xms20m -Djava.security.egd=file:/dev/./urandom\n" +
                "        name: JAVA_TOOL_OPTIONS\n" +
                "apiVersion: 1.0.0\n" +
                "commands:\n" +
                "  - name: gradle build\n" +
                "    actions:\n" +
                "      - workdir: '${CHE_PROJECTS_ROOT}/tdd-taxi-seed-java'\n" +
                "        type: exec\n" +
                "        command: gradle build\n" +
                "        component: gradle\n";

        eclipseCheClient.create(response.getToken(), devFile);

        // then
        assertThat(response.getToken()).isNotBlank();
    }
}
