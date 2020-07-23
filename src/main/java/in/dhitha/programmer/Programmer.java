package in.dhitha.programmer;

import java.util.Arrays;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.validation.validator.StringValidator;

@Slf4j
public class Programmer extends Panel {

  private String systemSelected = "DEC";
  private String numberFieldText = "0";
  private String decFieldText = "0";
  private String hexFieldText = "0";
  private String binFieldText = "0";
  private String octFieldText = "0";
  private Label decField;
  private Label hexField;
  private Label binField;
  private Label octField;

  private FeedbackPanel feedbackPanel;
  private TextField<String> numberField;

  public Programmer(String id) {
    super(id);

    feedbackPanel = new FeedbackPanel("feedback");
    feedbackPanel.setOutputMarkupId(true);
    add(feedbackPanel);

    List<String> numberSystem = Arrays.asList("DEC", "HEX", "BIN", "OCT");
    DropDownChoice<String> systemSelected =
        new DropDownChoice<>(
            "systemSelected", new PropertyModel<String>(this, "systemSelected"), numberSystem);
    systemSelected.add(AjaxFormComponentUpdatingBehavior.onUpdate("change", ajaxRequestTarget -> {
      convert(ajaxRequestTarget);
      setStringValidator(ajaxRequestTarget);
    }));

    numberField = new TextField<>("numberField", new PropertyModel<>(this, "numberFieldText"));
    numberField.add(StringValidator.maximumLength(18));
    numberField.setOutputMarkupId(true);
    numberField.add(AjaxFormComponentUpdatingBehavior.onUpdate("keyup", this::convert));
    numberField.add(AjaxFormComponentUpdatingBehavior.onUpdate("change", this::convert));

    decField = new Label("decField", new PropertyModel<String>(this, "decFieldText"));
    decField.setOutputMarkupId(true);

    binField = new Label("binField", new PropertyModel<String>(this, "binFieldText"));
    binField.setOutputMarkupId(true);

    hexField = new Label("hexField", new PropertyModel<String>(this, "hexFieldText"));
    hexField.setOutputMarkupId(true);

    octField = new Label("octField", new PropertyModel<String>(this, "octFieldText"));
    octField.setOutputMarkupId(true);

    add(feedbackPanel);
    add(systemSelected);
    add(numberField);
    add(decField);
    add(binField);
    add(hexField);
    add(octField);
  }

  private void convert(AjaxRequestTarget ajaxRequestTarget) {
    try {
      Long decimalValue = ProgrammerUtil.toDecimal(systemSelected, numberFieldText);
      decFieldText = decimalValue.toString();
      hexFieldText = ProgrammerUtil.decimalToHex(decimalValue);
      binFieldText = ProgrammerUtil.decimalToBinary(decimalValue);
      octFieldText = ProgrammerUtil.decimalToOctal(decimalValue);
      ajaxRequestTarget.add(decField);
      ajaxRequestTarget.add(hexField);
      ajaxRequestTarget.add(binField);
      ajaxRequestTarget.add(octField);
      ajaxRequestTarget.add(feedbackPanel);

    } catch (Exception exception) {
      feedbackPanel.error("Please enter valid input");
      decFieldText = "";
      hexFieldText = "";
      binFieldText = "";
      octFieldText = "";
      ajaxRequestTarget.add(decField);
      ajaxRequestTarget.add(hexField);
      ajaxRequestTarget.add(binField);
      ajaxRequestTarget.add(octField);
      ajaxRequestTarget.add(feedbackPanel);
    }
  }

  private void setStringValidator(AjaxRequestTarget ajaxRequestTarget){
    switch (systemSelected){
      default:
      case "DEC":
         numberField.add(StringValidator.maximumLength(18)); ajaxRequestTarget.add(numberField); break;
      case "BIN":
        numberField.add(StringValidator.maximumLength(63)); ajaxRequestTarget.add(numberField); break;
      case "HEX":
        numberField.add(StringValidator.maximumLength(16)); ajaxRequestTarget.add(numberField); break;
      case "OCT":
        numberField.add(StringValidator.maximumLength(21)); ajaxRequestTarget.add(numberField); break;
    }
  }
}
