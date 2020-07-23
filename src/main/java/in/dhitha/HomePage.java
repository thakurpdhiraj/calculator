package in.dhitha;

import in.dhitha.date.DateDifference;
import in.dhitha.programmer.Programmer;
import in.dhitha.tofrompanels.area.Area;
import in.dhitha.tofrompanels.currency.Currency;
import in.dhitha.tofrompanels.length.Length;
import in.dhitha.tofrompanels.speed.Speed;
import in.dhitha.tofrompanels.time.Time;
import in.dhitha.tofrompanels.volume.Volume;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.markup.html.WebPage;

public class HomePage extends WebPage {
	private static final long serialVersionUID = 1L;

	public HomePage(final PageParameters parameters) {
		super(parameters);
		add(new Currency("currency"));
		add(new Programmer("programmer"));
		add(new Area("area"));
		add(new Time("time"));
		add(new Length("length"));
		add(new Volume("volume"));
		add(new Speed("speed"));
		add(new DateDifference("date"));
	}
}
