package in.dhitha.tofrompanels.currency;

import de.agilecoders.wicket.extensions.markup.html.bootstrap.form.AbstractDateTextField.Event;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.form.DateTextField;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.form.DateTextField.IAjaxEventHandler;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.form.DateTextFieldConfig;
import in.dhitha.tofrompanels.parent.Parent;
import java.util.Date;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.PropertyModel;
import org.joda.time.DateTime;

@Slf4j
public class Currency extends Parent {
  protected DateTextField dateTextField ;
  protected Date selectedDate = new Date();

  public Currency(String id) {
    super(id,"Currency");

    List<String> dropDownList = getDropDownList();
    fromSelected = dropDownList.get(0);
    toSelected = dropDownList.get(0);

    dateTextField =
        new DateTextField(
            "datePicker",
            new PropertyModel<Date>(this, "selectedDate"),
            new DateTextFieldConfig()
                .highlightToday(true)
                .autoClose(true)
                .withFormat("yyyy-MM-dd")
                .withEndDate(new DateTime().minus(1)));

    dateTextField.addAjaxEvent(
        Event.changeDate, (IAjaxEventHandler) (target, date, event) -> {
          CurrencyUtil.getExchangeRate(selectedDate);
          changeToField(target);
        }, true);
    add(dateTextField);
  }

  @Override
  protected void onBeforeRender() {
    super.onBeforeRender();
    CurrencyUtil.getExchangeRate(selectedDate);
  }

  @Override
  protected void changeToField(AjaxRequestTarget ajaxRequestTarget) {
    super.changeToField(ajaxRequestTarget);
    Map<String, Double> returnMap = CurrencyUtil.getExchangeValue(fromValue, toValue , fromSelected, toSelected);
    toValue = returnMap.get("toValue");
    ajaxRequestTarget.add(toField);
  }

  @Override
  protected void changeFromField(AjaxRequestTarget ajaxRequestTarget) {
    super.changeFromField(ajaxRequestTarget);
    Map<String, Double> returnMap = CurrencyUtil.getExchangeValue(fromValue, toValue , fromSelected, toSelected);
    toValue = returnMap.get("fromValue");
    ajaxRequestTarget.add(fromField);
  }

  @Override
  protected List<String> getDropDownList() {
    return CurrencyUtil.currencyList;
  }

}
