package be.eekhaut.kristof.tournament.test.architecture;

import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchCondition;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.ConditionEvents;
import com.tngtech.archunit.lang.SimpleConditionEvent;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

@AnalyzeClasses(packages = "be.eekhaut.kristof.tournament")
class TestClassArchRules {

    @ArchTest
    static final ArchRule test_classes_should_reside_in_same_package = classes()
        .should(new ArchCondition<>("have their test in the same package") {

            private HashMap<String, Set<String>> classesBySimpleName;

            @Override
            public void init(Iterable<JavaClass> allClasses) {
                classesBySimpleName = new HashMap<>();
                allClasses.forEach(clazz ->
                    classesBySimpleName.computeIfAbsent(clazz.getSimpleName(), className -> new HashSet<>())
                        .add(clazz.getPackageName())
                );
            }

            @Override
            public void check(JavaClass item, ConditionEvents events) {
                if (item.getSimpleName().endsWith("Test")) {
                    String expectedImplementationClassName = item.getSimpleName().substring(0, item.getSimpleName().lastIndexOf("Test"));
                    Set<String> matchingPackageNames = classesBySimpleName.get(expectedImplementationClassName);
                    boolean satisfied = matchingPackageNames == null
                        || matchingPackageNames.contains(item.getPackageName());

                    if (!satisfied) {
                        String message = String.format("test class %s doesn't reside in same package as %s",
                            item.getName(),
                            expectedImplementationClassName);
                        events.add(new SimpleConditionEvent(item, satisfied, message));
                    }
                }
            }
        });
}
