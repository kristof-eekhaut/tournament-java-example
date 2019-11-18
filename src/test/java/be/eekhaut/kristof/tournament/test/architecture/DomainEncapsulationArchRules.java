package be.eekhaut.kristof.tournament.test.architecture;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.syntax.elements.ClassesShouldConjunction;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

@AnalyzeClasses(packages = "be.eekhaut.kristof.tournament", importOptions = ImportOption.DoNotIncludeTests.class)
class DomainEncapsulationArchRules {

    @ArchTest
    static final ArchRule tournaments_domain_should_not_be_accessed_by_other_domain = domainShouldNotBeAccessedByOtherDomain("tournament");
    @ArchTest
    static final ArchRule tournaments_application_should_not_be_accessed_by_other_domain = applicationShouldNotBeAccessedByOtherDomain("tournament");

    private static ClassesShouldConjunction domainShouldNotBeAccessedByOtherDomain(String domainPackageName) {
        return classes()
            .that().resideInAPackage(".." + domainPackageName + ".domain..")
                .and().areNotAssignableTo(Enum.class)
            .should().onlyBeAccessed().byAnyPackage(".." + domainPackageName + "..");
    }

    private static ClassesShouldConjunction applicationShouldNotBeAccessedByOtherDomain(String domainPackageName) {
        return classes()
                .that().resideInAPackage(".." + domainPackageName + ".app..")
                .and().areNotAssignableTo(Enum.class)
                .should().onlyBeAccessed().byAnyPackage(".." + domainPackageName + "..");
    }
}
