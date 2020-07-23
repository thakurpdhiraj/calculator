package in.dhitha.date;

import de.agilecoders.wicket.core.markup.html.bootstrap.form.BootstrapRadioChoice;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.form.AbstractDateTextField.Event;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.form.DateTextField;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.form.DateTextField.IAjaxEventHandler;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.form.DateTextFieldConfig;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.extern.slf4j.Slf4j;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormChoiceComponentUpdatingBehavior;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.util.value.IValueMap;

@Slf4j
public class DateDifference extends Panel {

  private WebMarkupContainer diffContainer;
  private WebMarkupContainer calcContainer;
  private FeedbackPanel feedbackPanel;
  private int selectedValue = 0;

  private DateTextField fromDiffDate;
  private Date fromDateDiffField = new Date();

  private DateTextField toDiffDate;
  private Date toDateDiffField = new Date();

  private Label outputDiff;
  private String outputDiffField = "Years: 0, Weeks: 0, Days: 0";

  private DateTextField fromCalcDate;
  private Date fromDateCalcField = new Date();

  private int operationValue = 0;

  private DropDownChoice<Integer> yearCalc;
  private int yearCalcField;

  private DropDownChoice<Integer> weekCalc;
  private int weekCalcField;

  private DropDownChoice<Integer> dayCalc;
  private int dayCalcField;

  private Label outputCalc;
  private String outputCalcField;

  public DateDifference(String id) {
    super(id);

    feedbackPanel = new FeedbackPanel("feedback");
    feedbackPanel.setOutputMarkupId(true);
    add(feedbackPanel);

    HashMap<Integer, String> selectMap = new HashMap<>();
    selectMap.put(0, "Difference between Dates");
    selectMap.put(1, "Add or subtract Dates");

    DropDownChoice<Integer> select =
        new DropDownChoice<>(
            "select",
            new PropertyModel<>(this, "selectedValue"),
            (IModel<List<Integer>>) () -> new ArrayList<>(selectMap.keySet()),
            new ChoiceRenderer<Integer>() {
              @Override
              public String getDisplayValue(Integer object) {
                return selectMap.get(object);
              }

              @Override
              public String getIdValue(Integer object, int index) {
                return object.toString();
              }
            });

    select.add(
        AjaxFormComponentUpdatingBehavior.onUpdate(
            "change",
            target -> {
              target.add(diffContainer.setVisible(selectedValue == 0));
              target.add(calcContainer.setVisible(selectedValue == 1));
            }));

    add(select);

    // Date Difference
    diffContainer = new WebMarkupContainer("diffContainer");
    diffContainer.setOutputMarkupId(true);
    diffContainer.setOutputMarkupPlaceholderTag(true);
    diffContainer.setVisible(true);

    fromDiffDate =
        new DateTextField(
                "fromDiffDate",
                new PropertyModel<Date>(this, "fromDateDiffField"),
                new DateTextFieldConfig()
                    .highlightToday(true)
                    .autoClose(true)
                    .withFormat("yyyy-MM-dd"))
            .addAjaxEvent(
                Event.changeDate,
                (IAjaxEventHandler)
                    (target, date, event) -> {
                      outputDiff(target);
                    },
                true);
    fromDiffDate.setOutputMarkupId(true);

    toDiffDate =
        new DateTextField(
                "toDiffDate",
                new PropertyModel<Date>(this, "toDateDiffField"),
                new DateTextFieldConfig()
                    .highlightToday(true)
                    .autoClose(true)
                    .withFormat("yyyy-MM-dd"))
            .addAjaxEvent(
                Event.changeDate,
                (IAjaxEventHandler)
                    (target, date, event) -> {
                      outputDiff(target);
                    },
                true);
    toDiffDate.setOutputMarkupId(true);

    outputDiff = new Label("outputDiff", PropertyModel.of(this, "outputDiffField"));
    outputDiff.setOutputMarkupId(true);

    diffContainer.add(fromDiffDate);
    diffContainer.add(toDiffDate);
    diffContainer.add(outputDiff);
    add(diffContainer);

    // Date Calculation
    calcContainer = new WebMarkupContainer("calcContainer");
    calcContainer.setOutputMarkupId(true);
    calcContainer.setOutputMarkupPlaceholderTag(true);
    calcContainer.setVisible(false);

    fromCalcDate =
        new DateTextField(
                "fromCalcDate",
                new PropertyModel<Date>(this, "fromDateCalcField"),
                new DateTextFieldConfig()
                    .highlightToday(true)
                    .autoClose(true)
                    .withFormat("dd-MMM-yyyy"))
            .addAjaxEvent(
                Event.changeDate,
                (IAjaxEventHandler)
                    (target, date, event) -> {
                      outputDiff(target);
                    },
                true);
    fromCalcDate.setOutputMarkupId(true);

    HashMap<Integer, String> operationMap = new HashMap<>();
    operationMap.put(0, "Add");
    operationMap.put(1, "Subtract");

    BootstrapRadioChoice<Integer> operationRadio =
        new BootstrapRadioChoice<Integer>(
            "operationRadio",
            new PropertyModel<>(this, "operationValue"),
            (IModel<List<Integer>>) () -> new ArrayList<>(operationMap.keySet()),
            new ChoiceRenderer<Integer>() {
              @Override
              public String getDisplayValue(Integer object) {
                return operationMap.get(object);
              }

              @Override
              public String getIdValue(Integer object, int index) {
                return object.toString();
              }
            }) {

          @Override
          protected IValueMap getAdditionalAttributes(int index, Integer choice) {
            IValueMap attrs = super.getAdditionalAttributes(index, choice);
            attrs.put("class", "form-check-input mx-2");
            return attrs;
          }

          @Override
          protected IValueMap getAdditionalAttributesForLabel(int index, Integer choice) {
            IValueMap attrs = super.getAdditionalAttributesForLabel(index, choice);
            attrs.put("style", "margin-right:5em;");
            return attrs;
          }
        };
    operationRadio
        .setInline(true)
        .add(AjaxFormChoiceComponentUpdatingBehavior.onUpdateChoice(this::outputCalc));

    List<Integer> numberList = IntStream.range(0, 1000).boxed().collect(Collectors.toList());

    yearCalc =
        new DropDownChoice<>("yearCalc", PropertyModel.of(this, "yearCalcField"), numberList);
    yearCalc
        .add(AjaxFormComponentUpdatingBehavior.onUpdate("change", this::outputCalc))
        .setOutputMarkupId(true);

    weekCalc =
        new DropDownChoice<>("weekCalc", PropertyModel.of(this, "weekCalcField"), numberList);
    weekCalc
        .add(AjaxFormComponentUpdatingBehavior.onUpdate("change", this::outputCalc))
        .setOutputMarkupId(true);

    dayCalc = new DropDownChoice<>("dayCalc", PropertyModel.of(this, "dayCalcField"), numberList);
    dayCalc
        .add(AjaxFormComponentUpdatingBehavior.onUpdate("change", this::outputCalc))
        .setOutputMarkupId(true);

    outputCalc = new Label("outputCalc", PropertyModel.of(this, "outputCalcField"));
    outputCalc.setOutputMarkupId(true);

    outputCalcField = new SimpleDateFormat("dd-MMM-yyyy").format(new Date());

    calcContainer.add(fromCalcDate);
    calcContainer.add(yearCalc);
    calcContainer.add(weekCalc);
    calcContainer.add(dayCalc);
    calcContainer.add(outputCalc);
    calcContainer.add(operationRadio);
    add(calcContainer);
  }

  private void outputCalc(AjaxRequestTarget ajaxRequestTarget) {
    LocalDate localDate = fromDateCalcField.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    if (operationValue == 0) { // add
      outputCalcField = localDate.plusDays(dayCalcField).plusWeeks(weekCalcField).plusYears(yearCalcField).format(
          DateTimeFormatter.ofPattern("dd-MMM-yyyy"));
    } else { // subtract
      outputCalcField = localDate.minusDays(dayCalcField).minusWeeks(weekCalcField).minusYears(yearCalcField).format(
          DateTimeFormatter.ofPattern("dd-MMM-yyyy"));
    }
    ajaxRequestTarget.add(outputCalc);
  }

  private void outputDiff(AjaxRequestTarget target) {
    LocalDate fromLocal =
        fromDateDiffField.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    LocalDate toLocal = toDateDiffField.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

    ChronoUnit.WEEKS.between(fromLocal, toLocal);

    log.info(
        "Years: {}, Week: {}, Days: {}",
        ChronoUnit.YEARS.between(fromLocal, toLocal),
        ChronoUnit.WEEKS.between(fromLocal, toLocal),
        ChronoUnit.DAYS.between(fromLocal, toLocal));

    outputDiffField =
        String.format(
            "Years: %d, Week: %d, Days: %d",
            ChronoUnit.YEARS.between(fromLocal, toLocal),
            ChronoUnit.WEEKS.between(fromLocal, toLocal),
            ChronoUnit.DAYS.between(fromLocal, toLocal));

    log.info("Output {}", outputDiffField);
    target.add(outputDiff);
  }
}
