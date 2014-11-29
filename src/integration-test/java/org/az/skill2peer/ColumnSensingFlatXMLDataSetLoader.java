package org.az.skill2peer;

import java.io.InputStream;

import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.springframework.core.io.Resource;

import com.github.springtestdbunit.dataset.AbstractDataSetLoader;

public class ColumnSensingFlatXMLDataSetLoader extends AbstractDataSetLoader {

    @Override
    protected IDataSet createDataSet(final Resource resource) throws Exception {
        final FlatXmlDataSetBuilder builder = new FlatXmlDataSetBuilder();
        builder.setColumnSensing(true);
        final InputStream inputStream = resource.getInputStream();
        try {
            return builder.build(inputStream);
        } finally {
            inputStream.close();
        }
    }

}
