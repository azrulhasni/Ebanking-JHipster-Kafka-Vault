package com.azrul.ebanking.transaction;

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
            .importPackages("com.azrul.ebanking.transaction");

        noClasses()
            .that()
                .resideInAnyPackage("com.azrul.ebanking.transaction.service..")
            .or()
                .resideInAnyPackage("com.azrul.ebanking.transaction.repository..")
            .should().dependOnClassesThat()
                .resideInAnyPackage("..com.azrul.ebanking.transaction.web..")
        .because("Services and repositories should not depend on web layer")
        .check(importedClasses);
    }
}
