package org.az.skill2peer.nuclei.model;

import static org.az.skill2peer.nuclei.model.BaseEntityAssert.assertThat;

import org.az.skill2peer.nuclei.common.model.BaseEntity;
import org.joda.time.DateTime;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

public class BaseEntityTest {

    @Test
    public void prePersist_ShouldSetCreationAndModificationTime() {
        final TestEntity entity = new TestEntity();

        entity.prePersist();

        assertThat(entity)
                .creationTimeIsSet()
                .modificationTimeIsSet()
                .creationTimeAndModificationTimeAreEqual();
    }

    @Test
    public void preUpdated_ShouldUpdateModificationTime() {
        final DateTime yesterday = DateTime.now().minusDays(1);

        final TestEntity entity = new TestEntityBuilder()
                .creationTime(yesterday)
                .modificationTime(yesterday)
                .build();

        entity.preUpdate();

        assertThat(entity)
                .creationTimeIsSet()
                .modificationTimeIsSet()
                .modificationTimeIsAfterCreationTime();
    }

    private static class TestEntity extends BaseEntity<Long> {
        private Long id;

        @Override
        public Long getId() {
            return id;
        }
    }

    private static class TestEntityBuilder {
        private final TestEntity build;

        private TestEntityBuilder() {
            build = new TestEntity();
        }

        private TestEntityBuilder creationTime(final DateTime creationTime) {
            ReflectionTestUtils.setField(build, "creationTime", creationTime);
            return this;
        }

        private TestEntityBuilder modificationTime(final DateTime modificationTime) {
            ReflectionTestUtils.setField(build, "modificationTime", modificationTime);
            return this;
        }

        private TestEntity build() {
            return build;
        }
    }
}
