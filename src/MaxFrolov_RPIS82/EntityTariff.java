package MaxFrolov_RPIS82;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class EntityTariff implements Tariff {
    node head;
    node tail;
    int size;

    public EntityTariff()
    {
        head=tail=null;
    }

    public EntityTariff(Service[] services)
    {
        if(services==null)
            throw new NullPointerException();
        head=tail=new node();
        head.service=services[0];
        for(int i=1;i<services.length;i++)
        {
            tail.next=new node(tail);
            tail=tail.next;
            tail.service=services[i];
        }
    }

    @Override
    public boolean add(Service service) {
        if(service==null)
            throw new NullPointerException();
        if(head!=null){
        tail.next=new node(tail);
        tail=tail.next;
        tail.service=service;}
        else {
            head=tail=new node();
            head.service=service;
        }
        return false;
    }

    @Override
    public boolean remove(Object o) {
        try {
            delete((Service) o);}
        catch (Exception e){return false;}
        return true;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object service:c) {
            try {add((Service) service);}
            catch (Exception e){return false;}

        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends Service> c) {
        for (Object service:c) {
            try {
                add((Service) service);}
            catch (Exception e){return false;}

        }
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        for (Object service:c) {
            try {


            delete((Service) service);}
            catch (Exception e){return false;}

        }
        return true;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public void clear() {
        head=tail=null;
    }

    @Override
    public boolean add(Service service, int pos) {
        if(service==null)
            throw new NullPointerException();
        if(pos<0||pos>=getSize())
            throw new IndexOutOfBoundsException();
        node current= head;
        for(int i=0;i<pos;i++)
        current=current.next;
        current.service=service;
        return false;
    }

    @Override
    public Service get(int pos) {
        if(pos<0||pos>=getSize())
            throw new IndexOutOfBoundsException();
        node current= head;
        for(int i=0;i<pos;i++)
            current=current.next;
        return current.service;
    }

    @Override
    public Service get(String name) {
        if(name==null)
            throw new NullPointerException();
        node current= head;
        do {
            if(current.service.getName().equals(name))
                return current.service;
            else current=current.next;
        }while (current!=null);
        return null;
    }

    @Override
    public boolean isIncluded(String name) {
        if(name==null)
            throw new NullPointerException();
        return get(name)!=null;
    }

    @Override
    public Service set(int pos, Service service) {
        if(service==null)
            throw new NullPointerException();
        if(pos<0||pos>=getSize())
            throw new IndexOutOfBoundsException();
        node current= head;
        for(int i=0;i<pos;i++)
            current=current.next;
        Service s=current.service;
        current.service=service;
        return s;
    }

    @Override
    public Service delete(int pos) {
        if(pos<0||pos>=getSize())
            throw new IndexOutOfBoundsException();
        node current= head;
        for(int i=0;i<pos;i++)
            current=current.next;
        Service s=current.service;
        current.previous.next=current.next;
        return s;
    }

    @Override
    public Service delete(String name) {
        if(name==null)
            throw new NullPointerException();
        node current= head;
        do {
            if(current.service.getName().equals(name))
            {
                Service s=current.service;
                current.previous.next=current.next;
                return s;
            }
            else current=current.next;
        }while (current!=null);
        return null;
    }

    @Override
    public int getSize() {
        node current= head;
        int n=0;
        do {
            n++;
            current=current.next;
        }while (current!=null);
        return n;
    }

    @Override
    public Service[] getServices() {
        int n=getSize();
        node current= head;
        Service[] services=new Service[n];
        for (int i=0;i<n;i++)
        {
            services[i]=current.service;
            current=current.next;
        }
        return services;
    }

    @Override
    public Service[] getSortedServices() {
        Service[] services=getServices();
        Arrays.sort(services);
        return services;
    }

    @Override
    public int getCost() {
        return 0;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder=new StringBuilder();
        stringBuilder.append("services:\n");
        for (Service serv:getServices()) {
            stringBuilder.append(serv.toString());
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    @Override
    public int hashCode() {
        int hash=71;
        for (Service serv:getServices()) hash*=serv.hashCode();
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        boolean res=false;
        if(obj.getClass().equals(IndividualsTariff.class))
        {
            IndividualsTariff tariff= (IndividualsTariff) obj;
            res=(tariff.getSize()==getSize());
            Service[] services=tariff.getServices();
            Service[] services1=getServices();
            for (int i=0;i<getSize();i++) {
                res= services[i].equals(services1[i]);
                if(!res)return res;
            }
            return res;
        }
        return false;
    }

    @Override
    public Service delete(Service service) {
        if(service==null)
            throw new NullPointerException();
        int index=firstIndex(service);
        if(index>0)
            return delete(index);
        else return null;
    }

    @Override
    public int firstIndex(Service service) {
        if(service==null)
            throw new NullPointerException();
        Service[] services=getServices();
        for (int i=0;i<getSize();i++)
            if(services[i].equals(service))return i;
        return -1;
    }

    @Override
    public Iterator<Service> iterator() {
        return new ServiceIterator();
    }

    class ServiceIterator implements  Iterator<Service>{
         int index=0;


        @Override
        public boolean hasNext() {
            return index<getSize();
        }

        @Override
        public Service next() {
            if(hasNext())
            return get(index++);
            else throw new NoSuchElementException();
        }
    }

    @Override
    public int size() {
        return getSize();
    }

    @Override
    public boolean isEmpty() {
        return size==0;
    }

    @Override
    public boolean contains(Object o) {
        for(Service service:getServices())
            if(service.equals(o))return true;
        return false;
    }



    @Override
    public Object[] toArray() {
        return getServices();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return (T[]) getServices();
    }
}
