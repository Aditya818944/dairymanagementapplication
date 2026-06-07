package 	com.dairy.management.application.bl.exceptions;
import java.util.*;
public class BLException extends Exception
{
private String genericException;
private Map<String,String> exceptions;
public BLException(String message)
{
super(message);
}
public BLException()
{
this.genericException=null;
this.exceptions=new HashMap<>();
}
public void setGenericException(String genericException)
{
this.genericException=genericException;
}
public String getGenericException()
{
return this.genericException;
}
public void addException(String property,String exception)
{
this.exceptions.put(property,exception);
}
public String getException(String property)
{
return this.exceptions.get(property);
}
public int getExceptionsCount()
{
return this.exceptions.size();
}
public void removeException(String property)
{
this.exceptions.remove(property);
}
public boolean hasException(String property)
{
return this.exceptions.containsKey(property);
}
public boolean hasGenericException()
{
return this.genericException!=null;
}
public boolean hasExceptions()
{
return this.exceptions.size()>0;
}
public List<String> getProperties()
{
List<String> properties=new ArrayList<>();
this.exceptions.forEach((k,v)->{properties.add(k);});
return properties;
}
public String getMessage()
{
if(this.genericException==null) return "";
return this.genericException;
}
}