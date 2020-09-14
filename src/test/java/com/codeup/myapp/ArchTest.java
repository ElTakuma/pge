package com.codeup.myapp;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {

        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("com.codeup.myapp");

        noClasses()
            .that()
                .resideInAnyPackage("com.codeup.myapp.service..")
            .or()
                .resideInAnyPackage("com.codeup.myapp.repository..")
            .should().dependOnClassesThat()
                .resideInAnyPackage("..com.codeup.myapp.web..")
        .because("Services and repositories should not depend on web layer")
        .check(importedClasses);
    }
}
