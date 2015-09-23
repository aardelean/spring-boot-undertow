package home.boot;

import io.undertow.predicate.Predicates;
import io.undertow.server.HandlerWrapper;
import io.undertow.server.HttpHandler;
import io.undertow.server.handlers.PathHandler;
import io.undertow.server.handlers.PredicateHandler;
import io.undertow.server.handlers.cache.DirectBufferCache;
import io.undertow.server.handlers.resource.*;
import io.undertow.servlet.ServletExtension;
import io.undertow.servlet.api.DeploymentInfo;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.xnio.BufferAllocator;

import javax.servlet.ServletContext;
import java.util.Map;

/**
 * Created by alex on 9/6/2015.
 */
public class NonBlockingServletExtension implements ServletExtension{

    private final static int metadataCacheSize = 16 * 1024;
    private final static int maxMemory = 1000 * 10 * 1000;
    private final static int slicesPerPage = 10;
    private final static int maxAge = 2000000;
    @Override
    public void handleDeployment(DeploymentInfo deploymentInfo, final ServletContext servletContext) {
        deploymentInfo.addInitialHandlerChainWrapper(new HandlerWrapper() {

            @Override
            public HttpHandler wrap(final HttpHandler handler) {

                WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(servletContext);
                Map<String, Object> handlers = ctx.getBeansWithAnnotation(HandlerPath.class);

                PathHandler rootHandler = new PathHandler();
                rootHandler.addPrefixPath("/", handler);
                for(Map.Entry<String, Object> handlerEntry : handlers.entrySet()){
                    if(handlerEntry.getValue() instanceof HttpHandler){
                        HttpHandler httpHandler = (HttpHandler) handlerEntry.getValue();
                        String path = httpHandler.getClass().getAnnotation(HandlerPath.class).path();
                        rootHandler.addPrefixPath(path, httpHandler);
                    }
                }
                asyncFileHandler(handler, rootHandler);
                return rootHandler;

            }
        });
    }

    private void asyncFileHandler(HttpHandler handler, PathHandler rootHandler) {
        DirectBufferCache dataCache = new DirectBufferCache(metadataCacheSize,
                                                            slicesPerPage,
                                                            maxMemory,
                                                            BufferAllocator.DIRECT_BYTE_BUFFER_ALLOCATOR);
        ClassPathResourceManager underlyingResourceManager = new ClassPathResourceManager(NonBlockingServletExtension.class.getClassLoader());

        CachingResourceManager cachingResourceManager = new CachingResourceManager(metadataCacheSize,
                                                                                    metadataCacheSize,
                                                                                    dataCache,
                                                                                    underlyingResourceManager,
                                                                                    maxAge);
        final ResourceHandler resourceHandler = new ResourceHandler(cachingResourceManager);
        PredicateHandler predicateHandler = new PredicateHandler(Predicates.suffix("html"), resourceHandler, handler);
        rootHandler.addPrefixPath("/file",predicateHandler);
    }
}