package ph.com.mynt.parcelcostapi.config;

import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.KieModule;
import org.kie.api.builder.KieRepository;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.internal.io.ResourceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ph.com.mynt.parcelcostapi.constants.ParcelCostConstants;
import ph.com.mynt.parcelcostapi.service.GenerateUUIDService;
import ph.com.mynt.parcelcostapi.service.LoggingService;

@Configuration
public class DroolConfig {
    private KieServices kieServices = KieServices.Factory.get();
    private LoggingService loggingService;
    private String uuid = new GenerateUUIDService().generateUUID();

    @Autowired
    public DroolConfig(LoggingService loggingService) {
        this.loggingService = loggingService;
    }

    private KieFileSystem getKieFileSystem() {
        KieFileSystem kieFileSystem = kieServices.newKieFileSystem();
        kieFileSystem.write(ResourceFactory.newClassPathResource("pricing.drl"));
        return kieFileSystem;
    }
    @Bean
    public KieContainer getKieContainer() {
        loggingService.log(uuid, this.getClass().toString(), ParcelCostConstants.CONTAINER_CREATED, "");
        getKieRepository();
        KieBuilder kb = kieServices.newKieBuilder(getKieFileSystem());
        kb.buildAll();
        KieModule kieModule = kb.getKieModule();
        return kieServices.newKieContainer(kieModule.getReleaseId());
    }

    private void getKieRepository() {
        final KieRepository kieRepository = kieServices.getRepository();
        kieRepository.addKieModule(kieRepository::getDefaultReleaseId);
    }

    @Bean
    public KieSession getKieSession() {
        loggingService.log(uuid, this.getClass().toString(), ParcelCostConstants.SESSION_CREATED, "");
        return getKieContainer().newKieSession();
    }
}
