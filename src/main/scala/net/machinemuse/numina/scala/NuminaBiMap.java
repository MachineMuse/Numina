//package net.machinemuse.numina.scala;
//
//import net.machinemuse.numina.general.MuseLogger;
//
//import javax.annotation.Nullable;
//import java.util.HashMap;
//
///**
// * Author: MachineMuse (Claire Semple)
// * Created: 4:30 AM, 29/04/13
// *
// * Ported to Java by lehjr on 10/22/16.
// */
//public class MuseBiMap<S, T> {
//    private final HashMap<S, T> nameMap = new HashMap();
//    private final HashMap<T, S> elemMap = new HashMap();
//
//    @Nullable
//    public T get(S name) {
//        return this.nameMap.get(name);
//    }
//
//    public HashMap<S, T> nameMap() {
//        return this.nameMap;
//    }
//
//    public HashMap<T, S> elemMap() {
//        return this.elemMap;
//    }
//
//    public Iterable<T> elems() {
//        return this.nameMap().values();
//    }
//
//    public Iterable<S> names() {
//        return this.elemMap().values();
//    }
//
//    public T putName(S name, T elem) {
//        T match = this.nameMap().get(name);
//        if (match != null) {
//            MuseLogger.logError(name + " already a member!");
//            return elem;
//        } else {
//            nameMap.put(name, elem);
//            elemMap.put(elem, name);
//            return elem;
//        }
//    }
//
//    public S putElem(T elem, S name) {
//        T match = nameMap.get(name);
//        if (match != null) {
//            MuseLogger.logError(name + " already a member!");
//
//        } else {
//            nameMap.put(name, elem);
//            elemMap.put(elem, name);
//        }
//        return name;
//    }
//
//    public HashMap<S, T> apply() {
//        return this.nameMap();
//    }
//
//    public HashMap<T, S> inverse() {
//        return this.elemMap();
//    }
//
//    @Nullable
//    public S getName(T elem) {
//        return this.elemMap().get(elem);
//    }
//
//    @Nullable
//    public S removeElem(T elem) {
//        S name = this.getName(elem);
//        if (name != null) {
//            this.nameMap().remove(name);
//            this.elemMap().remove(elem);
//            return name;
//        }
//        return null;
//    }
//
//    @Nullable
//    public T removeName(S name) {
//        T elem = this.get(name);
//        if (elem != null) {
//            this.nameMap().remove(name);
//            this.elemMap().remove(elem);
//            return elem;
//        }
//        return null;
//    }
//}