package in.dhitha;

import de.agilecoders.wicket.core.Bootstrap;
import de.agilecoders.wicket.core.markup.html.bootstrap.block.prettyprint.PrettifyCssResourceReference;
import de.agilecoders.wicket.core.markup.html.bootstrap.block.prettyprint.PrettifyJavaScriptReference;
import de.agilecoders.wicket.core.markup.html.references.ModernizrJavaScriptReference;
import de.agilecoders.wicket.core.settings.BootstrapSettings;
import de.agilecoders.wicket.core.settings.CookieThemeProvider;
import de.agilecoders.wicket.core.settings.IBootstrapSettings;
import de.agilecoders.wicket.core.settings.ThemeProvider;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.html5player.Html5PlayerCssReference;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.html5player.Html5PlayerJavaScriptReference;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.icon.OpenWebIconsCssReference;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.jqueryui.JQueryUIJavaScriptReference;
import de.agilecoders.wicket.themes.markup.html.bootswatch.BootswatchTheme;
import de.agilecoders.wicket.themes.markup.html.bootswatch.BootswatchThemeProvider;
import in.dhitha.error.InternalServer;
import org.apache.wicket.ResourceBundles;
import org.apache.wicket.Session;
import org.apache.wicket.authorization.strategies.page.SimplePageAuthorizationStrategy;
import org.apache.wicket.core.request.handler.BookmarkableListenerRequestHandler;
import org.apache.wicket.core.request.handler.ListenerRequestHandler;
import org.apache.wicket.core.request.mapper.MountedMapper;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.IRequestHandler;
import org.apache.wicket.request.Url;
import org.apache.wicket.request.component.IRequestablePage;
import org.apache.wicket.request.mapper.info.PageComponentInfo;
import org.apache.wicket.request.mapper.parameter.PageParametersEncoder;
import org.apache.wicket.request.resource.CssResourceReference;
import org.apache.wicket.request.resource.JavaScriptResourceReference;
import org.apache.wicket.settings.RequestCycleSettings;

public class CalculatorApplication extends WebApplication
{
	@Override
	public Class<? extends WebPage> getHomePage()
	{
		return HomePage.class;
	}

	@Override
	public void init()
	{
		super.init();
		getApplicationSettings().setInternalErrorPage(InternalServer.class);
		getRootRequestMapperAsCompound().add(new NoVersionMapper("/calculator", HomePage.class));
		mountPage("/500", InternalServer.class);

		getRequestCycleSettings()
				.setRenderStrategy(RequestCycleSettings.RenderStrategy.REDIRECT_TO_RENDER);

		configureBootstrap();
		//configureResourceBundles();
	}

	@Override
	public void setServletContext(javax.servlet.ServletContext servletContext) {
		super.setServletContext(servletContext);
	}

	private void configureBootstrap() {
		final IBootstrapSettings settings = new BootstrapSettings();
		Bootstrap.builder().withBootstrapSettings(settings).install(this);
		final ThemeProvider themeProvider = new BootswatchThemeProvider(BootswatchTheme.Darkly);
		settings.setJsResourceFilterName("footer-container")
				.setThemeProvider(themeProvider)
				.setActiveThemeProvider(new CookieThemeProvider());
	}

	private void configureResourceBundles() {
		ResourceBundles bundles = getResourceBundles();
		bundles.addJavaScriptBundle(CalculatorApplication.class, "core.js",
				(JavaScriptResourceReference) getJavaScriptLibrarySettings().getJQueryReference(),
				(JavaScriptResourceReference) getJavaScriptLibrarySettings().getWicketAjaxReference(),
				ModernizrJavaScriptReference.instance()
		);

		bundles.addJavaScriptBundle(CalculatorApplication.class, "bootstrap.js",
				(JavaScriptResourceReference) Bootstrap.getSettings().getJsResourceReference(),
				(JavaScriptResourceReference) PrettifyJavaScriptReference.INSTANCE
		);

		getResourceBundles().addJavaScriptBundle(CalculatorApplication.class, "bootstrap-extensions.js",
				JQueryUIJavaScriptReference.instance(),
				Html5PlayerJavaScriptReference.instance()
		);

		bundles.addCssBundle(CalculatorApplication.class, "bootstrap-extensions.css",
				Html5PlayerCssReference.instance(),
				OpenWebIconsCssReference.instance()
		);

		bundles.addCssBundle(CalculatorApplication.class, "application.css",
				(CssResourceReference) PrettifyCssResourceReference.INSTANCE
		);
	}
	
	private static class NoVersionMapper extends MountedMapper {
		public NoVersionMapper(final Class<? extends IRequestablePage> pageClass) {
			this("/", pageClass);
		}

		public NoVersionMapper(String mountPath, Class<? extends IRequestablePage> pageClass) {
			super(mountPath, pageClass, new PageParametersEncoder());
		}

		@Override
		protected void encodePageComponentInfo(Url url, PageComponentInfo info) {
			// do nothing
		}

		@Override
		public Url mapHandler(IRequestHandler requestHandler) {
			if (requestHandler instanceof ListenerRequestHandler
					|| requestHandler instanceof BookmarkableListenerRequestHandler) {
				return null;
			} else {
				return super.mapHandler(requestHandler);
			}
		}
	}
}
