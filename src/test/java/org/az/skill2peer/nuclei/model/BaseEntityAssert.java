package org.az.skill2peer.nuclei.model;

import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.Assertions;
import org.az.skill2peer.nuclei.common.model.BaseEntity;
import org.joda.time.DateTime;

public class BaseEntityAssert extends AbstractAssert<BaseEntityAssert, BaseEntity<?>> {

    public BaseEntityAssert(final BaseEntity<?> actual) {
        super(actual, BaseEntityAssert.class);
    }

    public static BaseEntityAssert assertThat(final BaseEntity<?> actual) {
        return new BaseEntityAssert(actual);
    }

    public BaseEntityAssert creationTimeAndModificationTimeAreEqual() {
        isNotNull();

        Assertions
                .assertThat(actual.getCreationTime())
                .overridingErrorMessage("Expected creation time to be equal than modification time but were <%s> and <%s>",
                        actual.getCreationTime(),
                        actual.getModificationTime()
                )
                .isEqualTo(actual.getModificationTime());

        return this;
    }

    public BaseEntityAssert creationTimeIsSet() {
        isNotNull();

        Assertions.assertThat(actual.getCreationTime())
                .overridingErrorMessage("Expected creation time to be set but was <null>")
                .isNotNull();

        return this;
    }

    public BaseEntityAssert modificationTimeIsAfterCreationTime() {
        isNotNull();

        final DateTime creationTime = actual.getCreationTime();
        final DateTime modificationTime = actual.getModificationTime();

        Assertions.assertThat(modificationTime.isAfter(creationTime))
                .overridingErrorMessage("Expected modification time to be after creation time but were <%s> and <%s>",
                        actual.getModificationTime(),
                        actual.getCreationTime()
                )
                .isTrue();

        return this;
    }

    public BaseEntityAssert modificationTimeIsSet() {
        isNotNull();

        Assertions.assertThat(actual.getModificationTime())
                .overridingErrorMessage("Expected modification time be set was <null>")
                .isNotNull();

        return this;
    }
}
