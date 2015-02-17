package ${model.packageName};
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import ${model.daoPath}.${model.daoName};
@Service
public class ${model.className} implements ${model.serviceName}{
   @Resource
   private ${model.daoName} dao;
}