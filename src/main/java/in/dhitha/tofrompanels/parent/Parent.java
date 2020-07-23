package in.dhitha.tofrompanels.parent;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.NumberTextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

@Slf4j
public abstract class Parent extends Panel {

  DropDownChoice<String> fromSelect;
  DropDownChoice<String> toSelect;
  protected FeedbackPanel feedbackPanel;
  protected NumberTextField<Double> toField;
  protected Double toValue = 0.0;
  protected NumberTextField<Double> fromField;
  protected Double fromValue = 0.0;
  protected String fromSelected;
  protected String toSelected;
  protected Label appName;

  public Parent(String id,String appNameValue) {
    super(id);

    appName = new Label("appName", Model.of(appNameValue));

    feedbackPanel = new FeedbackPanel("feedback");
    feedbackPanel.setOutputMarkupId(true);
    add(feedbackPanel);

    List<String> dropDownList = getDropDownList();
    fromSelected = dropDownList.get(0);
    toSelected = dropDownList.get(0);

    fromSelect =
        new DropDownChoice<>(
            "fromSelect", PropertyModel.of(this, "fromSelected"), dropDownList);
    fromSelect.add(AjaxFormComponentUpdatingBehavior.onUpdate("change", this::changeFromField));

    toSelect =
        new DropDownChoice<>(
            "toSelect", PropertyModel.of(this, "toSelected"), dropDownList);
    toSelect.add(AjaxFormComponentUpdatingBehavior.onUpdate("change", this::changeToField));

    fromField = new NumberTextField<>("fromField", PropertyModel.of(this, "fromValue"),Double.class);
    fromField.setOutputMarkupId(true);
    fromField.add(AjaxFormComponentUpdatingBehavior.onUpdate("keyup", this::changeToField));
    fromField.add(AjaxFormComponentUpdatingBehavior.onUpdate("change", this::changeToField));

    toField = new NumberTextField<>("toField", PropertyModel.of(this,"toValue"),Double.class);
    toField.setOutputMarkupId(true);
    toField.add(AjaxFormComponentUpdatingBehavior.onUpdate("keyup", this::changeFromField));
    toField.add(AjaxFormComponentUpdatingBehavior.onUpdate("change", this::changeFromField));

    add(appName);
    add(feedbackPanel);
    add(fromSelect);
    add(toSelect);
    add(fromField);
    add(toField);
  }

  protected  void changeToField(AjaxRequestTarget ajaxRequestTarget){
    fromValue = fromValue == null ? 0.0 : fromValue;
    toValue = toValue == null ? 0.0 : toValue;
  };

  protected  void changeFromField(AjaxRequestTarget ajaxRequestTarget){
    fromValue = fromValue == null ? 0.0 : fromValue;
    toValue = toValue == null ? 0.0 : toValue;
  };

  protected  abstract List<String> getDropDownList();
}
