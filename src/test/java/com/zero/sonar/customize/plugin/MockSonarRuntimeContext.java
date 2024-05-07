package com.zero.sonar.customize.plugin;

import org.sonar.api.SonarEdition;
import org.sonar.api.SonarProduct;
import org.sonar.api.SonarQubeSide;
import org.sonar.api.SonarRuntime;
import org.sonar.api.utils.Version;

/**
 * Mocked SonarQube Runtime Context
 * @author zero
 * @since 2024/5/6 14:31
 */
public class MockSonarRuntimeContext implements SonarRuntime {
    @Override
    public Version getApiVersion() {
        return Version.create(9, 9);
    }

    @Override
    public SonarProduct getProduct() {
        return SonarProduct.SONARQUBE;
    }

    @Override
    public SonarQubeSide getSonarQubeSide() {
        return SonarQubeSide.SCANNER;
    }

    @Override
    public SonarEdition getEdition() {
        return SonarEdition.COMMUNITY;
    }
}
