package sn.ept.git.seminaire.cicd;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

public class ArchTest {


    public static final String BASE="sn.ept.git.seminaire.cicd";
    public static final String SERVICES=BASE.concat(".services..");
    public static final String SERVICES_IMPL=BASE.concat(".services.impl..");
    public static final String REPOSITORY=BASE.concat(".repositories..");
    public static final String MODELS=BASE.concat(".models..");
    public static final String COMPONENTS=BASE.concat(".component..");
    public static final String RESOURCE=BASE.concat(".resources..");
    static JavaClasses importedClasses;

    @BeforeAll
    static void init(){
        importedClasses = new ClassFileImporter()
                .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
                .importPackages(BASE);
    }


    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {
        noClasses()
                .that()
                .resideInAnyPackage(SERVICES)
                .or()
                .resideInAnyPackage(SERVICES_IMPL)
                .or()
                .resideInAnyPackage(REPOSITORY)
                .or()
                .resideInAnyPackage(COMPONENTS)
                .should()
                .dependOnClassesThat()
                .resideInAnyPackage(RESOURCE)
                .because("Services, components and repositories should not depend on web layer")
                .check(importedClasses);
    }

    @Test
    void resourcesShouldNotDependOnRepositoryLayer() {
        noClasses()
                .that()
                .resideInAnyPackage(RESOURCE)
                .should()
                .dependOnClassesThat()
                .resideInAnyPackage(REPOSITORY)
                .because("Resources should not depend on Repository layer")
                .check(importedClasses);
    }


    @Test
    void servicesShouldNotDependOnServiceLayer() {
        noClasses()
                .that()
                .resideInAnyPackage(SERVICES_IMPL)
                .should()
                .dependOnClassesThat()
                .resideInAnyPackage(SERVICES_IMPL)
                .because("Services should not depend on Service layer")
                .check(importedClasses);
    }

    @Test
    void resourcesNamesShouldEndWithResource() {
        classes()
                .that()
                .resideInAPackage(RESOURCE)
                .should()
                .haveSimpleNameEndingWith("Resource")
                .because("Resource name should end with Resource")
                .check(importedClasses);
    }

    @Test
    void onlyRepositoriesSouldBeAnnotatedWithRepository() {
        classes()
                .that()
                .areAnnotatedWith(org.springframework.stereotype.Repository.class)
                .should()
                .resideInAnyPackage(REPOSITORY)
                .because("Only repositories sould be annotated with @Repository")
                .check(importedClasses);
    }




    @Test
    void onlyServicesSouldBeAnnotatedWithService() {
        classes()
                .that()
                .areAnnotatedWith(org.springframework.stereotype.Service.class)
                .should()
                .resideInAnyPackage(SERVICES_IMPL)
                .because("Only services sould be annotated with @Service")
                .check(importedClasses);
    }

    @Test
    void onlyResourcesSouldBeAnnotatedWithRestController() {
        classes()
                .that()
                .areAnnotatedWith(org.springframework.web.bind.annotation.RestController.class)
                .should()
                .resideInAnyPackage(RESOURCE)
                .because("Only resources sould be annotated with @RestController")
                .check(importedClasses);
    }
    @Test
    void onlyDomaineClassesSouldBeAnnotatedWithEntity() {
        classes()
                .that()
                .areAnnotatedWith(javax.persistence.Entity.class)
                .should()
                .resideInAnyPackage(MODELS)
                .because("Only entities sould be annotated with @Entity")
                .check(importedClasses);
    }

    @Test
    void onlyDomaineClassesSouldBeAnnotatedWithTable() {
        classes()
                .that()
                .areAnnotatedWith(javax.persistence.Table.class)
                .should()
                .resideInAnyPackage(MODELS)
                .because("Only entities sould be annotated with @Table")
                .check(importedClasses);
    }


    @Test
    void onlyDomaineClassesSouldBeAnnotatedWithWhere() {
        classes()
                .that()
                .areAnnotatedWith(org.hibernate.annotations.Where.class)
                .should()
                .resideInAnyPackage(MODELS)
                .because("Only entities sould be annotated with @Where")
                .check(importedClasses);
    }
}
