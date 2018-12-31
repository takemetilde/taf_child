package com.taf.basiccontrols;


import com.taf.auto.WebDriverUtil;
import com.taf.utils.Scroll;
import com.taf.utils.Wait.Condition;
import com.taf.utils.Wait.Wait;
import com.taf.utils.Wait.WebElementCondition;
import com.taf.utils.WebDriverUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.fail;

public class DropDown extends Displayable{
    final private WebElement primaryButton;
    final private WebElement activeOption;
    final private DropDownMenu dropDownMenu;

    public DropDown(WebElement dropDownElement) {
        this.activeOption = dropDownElement.findElement(By.className("psActiveOption"));
        this.primaryButton = dropDownElement.findElement(By.cssSelector("Button.btn-primary.psButton.btn"));
        dropDownMenu = new DropDownMenu(dropDownElement.findElement(By.className("psDropdown")));
    }

    public DropDown(By by) {
        WebElement dropDownElement = WebDriverUtils.getDriverInstance().findElement(by);
        this.activeOption = dropDownElement.findElement(By.className("psActiveOption"));
        this.primaryButton = dropDownElement.findElement(By.cssSelector("Button.btn-primary.psButton.btn"));
        dropDownMenu = new DropDownMenu(dropDownElement.findElement(By.className("psDropdown")));
    }

    @Override
    public boolean isDisplayed() {
        try {
            return primaryButton.isDisplayed();
        }
        catch (Exception ex) {
            return false;
        }
    }

    public boolean isExpanded() {
        return dropDownMenu.isExpanded();
    }

    public void expand() {
        dropDownMenu.expand();
    }

    public String activeOption() {
        return activeOption.getText();
    }

    public void select(String option) {
        dropDownMenu.expand();
        DropDownItem item = dropDownMenu.get(option);
        if(item != null)
            item.select();
        else
            fail("'" + option + "' is not a valid option for this dropdown!");
    }

    public DropDownItem get(int index) {
        return dropDownMenu.items.get(index);
    }

    public List<DropDownItem> getItems() {
        return dropDownMenu.items;
    }

    public class DropDownMenu {
        final private WebElement dropDownMenu;

        public DropDownMenu(WebElement dropDownMenu) {
            this.dropDownMenu = dropDownMenu;
            Wait.For(Condition.pageLoad(WebDriverUtil.driver()));
            items = new ArrayList<>();
            dropDownMenu.findElements(By.className("psLabel")).forEach(x-> items.add(new DropDownItem(x)));
        }

        private List<DropDownItem> items;

        public boolean isExpanded() {
            return dropDownMenu.getAttribute("class").contains("active");
        }

        public void expand() {
            if(!isExpanded()) {
                try {
                    Scroll.toCenter(primaryButton);
                    Wait.For(WebElementCondition.isDisplayed(primaryButton));
                    primaryButton.click();
                }
                catch(Exception ex) {
                    fail("Failed to expand the dropdown!");
                }
            }
        }

        public void collapse() {
            if(isExpanded())
                primaryButton.click();
        }

        public DropDownItem get(String option) {
            for(DropDownItem e : items) {
                if(e.getText().equalsIgnoreCase(option)) {
                    Scroll.toCenter(e.element);
                    return e;
                }
            }
            return null;
        }

    }

    public static class DropDownItem {
        final private WebElement element;

        protected DropDownItem(WebElement webElement) {
            element = webElement;
        }

        public String getText() {
            return element.getText();
        }

        public void select() {
            element.click();
        }
    }

}
