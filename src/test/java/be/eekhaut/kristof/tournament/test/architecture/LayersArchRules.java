package be.eekhaut.kristof.tournament.test.architecture;

import com.tngtech.archunit.base.DescribedPredicate;
import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.library.Architectures;

import static com.tngtech.archunit.base.DescribedPredicate.not;
import static com.tngtech.archunit.core.domain.JavaClass.Predicates.resideInAnyPackage;
import static com.tngtech.archunit.library.Architectures.layeredArchitecture;

@AnalyzeClasses(packages = "be.eekhaut.kristof.tournament", importOptions = ImportOption.DoNotIncludeTests.class)
class LayersArchRules {

    private static final String LAYER_ADAPTER = "Adapter";
    private static final String LAYER_API = "API";
    private static final String LAYER_APPLICATION = "Application";
    private static final String LAYER_DOMAIN = "Domain";

    @ArchTest
    static final ArchRule adapters_should_not_be_accessed_by_other_layers = layers()
            .whereLayer(LAYER_ADAPTER).mayNotBeAccessedByAnyLayer();

    @ArchTest
    static final ArchRule api_should_only_be_accessed_by_adapters_and_application = layers()
            .whereLayer(LAYER_API).mayOnlyBeAccessedByLayers(LAYER_ADAPTER, LAYER_APPLICATION);

    @ArchTest
    static final ArchRule application_should_only_be_accessed_by_adapters = layers()
            .whereLayer(LAYER_APPLICATION).mayOnlyBeAccessedByLayers(LAYER_ADAPTER);

    @ArchTest
    static final ArchRule domain_should_only_be_accessed_by_application_and_adapters = layers()
            .whereLayer(LAYER_DOMAIN).mayOnlyBeAccessedByLayers(LAYER_APPLICATION, LAYER_ADAPTER);

    private static Architectures.LayeredArchitecture layers() {
        return layeredArchitecture()
                .layer(LAYER_ADAPTER).definedBy(residesInLayerPackage("..adapters.."))
                .layer(LAYER_API).definedBy(residesInLayerPackage("..api.."))
                .layer(LAYER_APPLICATION).definedBy(residesInLayerPackage("..app.."))
                .layer(LAYER_DOMAIN).definedBy(residesInLayerPackage("..domain.."));
    }

    private static DescribedPredicate<JavaClass> residesInLayerPackage(String packageIdentifier) {
        return resideInAnyPackage(packageIdentifier)
                .and(not(resideInAnyPackage("be.eekhaut.kristof.tournament.common..")));
    }
}
