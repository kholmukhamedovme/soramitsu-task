package me.kholmukhamedov.soramitsutest.models.converter;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import me.kholmukhamedov.soramitsutest.models.domain.Item;
import me.kholmukhamedov.soramitsutest.models.presentation.ItemModel;

import static junit.framework.Assert.assertEquals;

public class DomainToPresentationConverterTest {

    private DomainToPresentationConverter mConverter;

    @Before
    public void setUp() {
        mConverter = new DomainToPresentationConverter();
    }

    @Test
    public void convert() {
        for (int i = 0; i < getFrom().size(); i++) {
            ItemModel actual = mConverter.convert(getFrom().get(i));

            assertEquals(actual, getExpected().get(i));
        }
    }

    @SuppressWarnings("ConstantConditions")
    @Test(expected = NullPointerException.class)
    public void convert_null() {
        mConverter.convert(null);
    }

    @Test
    public void convertList() {
        List<ItemModel> actual = mConverter.convertList(getFrom());

        assertEquals(actual, getExpected());
    }

    @Test
    public void convertList_empty() {
        List<ItemModel> actual = mConverter.convertList(Collections.emptyList());

        assertEquals(actual, Collections.emptyList());
    }

    @SuppressWarnings("ConstantConditions")
    @Test(expected = NullPointerException.class)
    public void convertList_null() {
        mConverter.convertList(null);
    }

    private List<Item> getFrom() {
        List<Item> itemList = new ArrayList<>();

        itemList.add(new Item(
                "IMG_20180803_193703570",
                "https://farm1.staticflickr.com/939/29031075737_33d2ab4b90_m.jpg"
        ));
        itemList.add(new Item(
                "Techniker Beachtour Zinnowitz",
                "https://farm2.staticflickr.com/1815/29031077737_4f3ae85cbd_m.jpg"
        ));

        return itemList;
    }

    private List<ItemModel> getExpected() {
        List<ItemModel> expectedItemList = new ArrayList<>();

        expectedItemList.add(new ItemModel(
                "IMG_20180803_193703570",
                "https://farm1.staticflickr.com/939/29031075737_33d2ab4b90_m.jpg"
        ));
        expectedItemList.add(new ItemModel(
                "Techniker Beachtour Zinnowitz",
                "https://farm2.staticflickr.com/1815/29031077737_4f3ae85cbd_m.jpg"
        ));

        return expectedItemList;
    }

}
