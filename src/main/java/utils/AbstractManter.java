package utils;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.modelmapper.ModelMapper;

public class AbstractManter<model, dto> extends LeCoffeeSession  {
	@PersistenceContext(unitName = "lecoffee")
	public EntityManager em;
	public ModelMapper converter;
	
	public Class<?> toClass;
	public Class<?> modelClass;
	
	public AbstractManter() {   
		if(em == null) {
			em = Jpa.getEntityManager();
		}
		
		this.setConverter(new ModelMapper());
	}
	
    public void setClassTypes(Class<model> modelClass, Class<dto> toClass) {
       this.setModelClass(modelClass);
       this.setToClass(toClass);
    }
	
    @SuppressWarnings("unchecked")
	public List<dto> convertModelResults(List<model> results) {
        return (List<dto>) results.stream()
                .map(model -> this.getConverter().map(model, this.getToClass()))
                .collect(Collectors.toList());
    }
	
	@SuppressWarnings("unchecked")
	public dto convertToDTO(model obj) {
		return (dto) this.getConverter().map(obj, this.getToClass());
	}
	
	@SuppressWarnings("unchecked")
	public model convertToModel(dto obj) {
		return (model) this.getConverter().map(obj, this.getModelClass());
	}

	// Getters and Setters
	public ModelMapper getConverter() {
		return converter;
	}
	public void setConverter(ModelMapper converter) {
		this.converter = converter;
	}

	public Class<?> getToClass() {
		return toClass;
	}

	public void setToClass(Class<?> toClass) {
		this.toClass = toClass;
	}

	public Class<?> getModelClass() {
		return modelClass;
	}

	public void setModelClass(Class<?> modelClass) {
		this.modelClass = modelClass;
	}
}
