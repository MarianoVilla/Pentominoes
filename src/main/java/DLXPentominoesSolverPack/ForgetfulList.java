package DLXPentominoesSolverPack;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

//List that only remembers the last N items. Asking for any previous items gives null.
public class ForgetfulList<T>
extends AbstractList<T>
{
private List<T> m_store = new ArrayList<T>();
private int m_numforgotten = 0;
private int m_limit;

public ForgetfulList() {
   m_limit = 0;
}
public ForgetfulList(int limit) {
   m_limit = limit;
}

public int NumForgotten(){
   return m_numforgotten;
}

@Override
public void clear() {
   m_store.clear();
   m_numforgotten=0;
}

@Override
public T get(int index) {
   if( index<0 || index>=size() ) throw new IndexOutOfBoundsException();
   if( index < m_numforgotten) return null;
   return m_store.get(m_limit==0 ? index : index%m_limit);
}

@Override
public int size() {
   return m_numforgotten + m_store.size();
}

@Override
public T set(int index, T v) {
   if( index<0 || index>=size() ) throw new IndexOutOfBoundsException();
   if( index<m_numforgotten) return null;
   return m_store.set(m_limit==0 ? index : index%m_limit,v);
}

@Override
public void add(int index, T v){
   if( index<0 || index>size() ) throw new IndexOutOfBoundsException();
   if( size()<m_limit || m_limit==0 ){
      m_store.add(index, v);
   }else{
      T v2 = v;
      int i = index;
      if( i<m_numforgotten ){
         v2=null;
         i = m_numforgotten;
      }
      while( i<size() ){
         v2 = set(i,v2);
         i++;
      }
      m_numforgotten++;
      set(i,v2);
   }
}

@Override
public T remove(int index){
   if( index<0 || index>=size() ) throw new IndexOutOfBoundsException();
   if( m_limit==0 || m_numforgotten == 0 ){
      return m_store.remove(index);
   }
   int i = size()-1;
   T v = get(i);
   m_numforgotten--;
   while( i>index && i>m_numforgotten){
      i--;
      v = set(i,v);
   }
   return v;
}
}
