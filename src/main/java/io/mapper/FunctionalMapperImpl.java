package io.mapper;

import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Mapper que recibe funciones, getters y setters y los ejecuta de manera que sean nullsafe
 * <pre>
 * functionalMapper.map(
 * mappedObject::setY,
 * toMapObject::getX,
 * X::getY );
 * <p>
 * EQUALS TO
 * <p>
 * Y y = null;
 * X x = null;
 * if(toMapObject!=null){
 * x = toMapObject.getX();
 * if(x!=null) {
 * y = x.getY()
 * }
 * }
 * mappedObject.setY(y);
 * </pre>
 */
@Component
public class FunctionalMapperImpl implements FunctionalMapper {

    /**
     * @param nullableValue  objeto del tipo que espera ejecutarse en el innergetter CALLER_TYPE
     * @param innerGetter    Ejecuta una funcion sobre un valor del tipo pasado como parámetro CALLER_TYPE y retorna un resultado del tipo RETURN_TYPE
     * @param <GETTER>       tipo generico -> X from getX()
     * @param <INNER_GETTER> tipo generico -> Z from getX().getZ();
     * @return InnerGetter type. <Z> Z z = anObject.getX().getZ();
     */
    public  <GETTER, INNER_GETTER> INNER_GETTER getNullSafeValue(GETTER nullableValue,
                                                                 final Function<GETTER, INNER_GETTER> innerGetter) {
        return Optional.ofNullable(nullableValue).map(notNullValue -> innerGetter.apply(notNullValue)).orElse(null);
    }

    /**
     * @param getter         Proveedor de resultados. Espera recibir una funcion que no tenga parámetros y retorna un resultado del tipo GETTER
     * @param innerGetter    Ejecuta una funcion sobre un valor del tipo retornado en el getter GETTER y retorna un resultado del tipo INNER_GETTER
     * @param <GETTER>       tipo generico -> X from getX()
     * @param <INNER_GETTER> tipo generico -> Z from getX().getZ(); (pero nullSafe)
     * @return
     */

    @Override
    public <GETTER, INNER_GETTER> INNER_GETTER getNullSafe(Supplier<GETTER> getter,
                                                           final Function<GETTER, INNER_GETTER> innerGetter) {
        return getNullSafeValue(getter.get(), innerGetter);
    }

    @Override
    public <GETTER, INNER_GETTER, INNER_INNER_GETTER> INNER_INNER_GETTER getNullSafe(Supplier<GETTER> getter, Function<GETTER, INNER_GETTER> innerGetter, Function<INNER_GETTER, INNER_INNER_GETTER> innerinnerGetter) {
        INNER_GETTER nullSafe = getNullSafe(getter, innerGetter);
        return getNullSafeValue(nullSafe,innerinnerGetter);
    }

    /**
     * @param setter        Consumidor de resultados. Espera recibir una funcion con un parámetro Y y no retorna nada. (un SET)
     * @param nullableValue objeto del tipo que espera recibir el setter. Y
     * @param <SETTER>      tipo generico -> Y from setY(y)
     */
    @Override
    public <SETTER> void mapValue(final Consumer<SETTER> setter, SETTER nullableValue) {
        if (null != nullableValue) {
            setter.accept(nullableValue);
        }
    }

    /**
     * @param setter   Consumidor de resultados. Espera recibir una funcion con un parámetro SETTER y no retorna nada. (un SET)
     * @param getter   Proveedor de resultados. Espera recibir una funcion que no tenga parámetros y retorna un resultado del tipo SETTER
     * @param <SETTER> tipo generico -> Y from setY(y)
     *                 <p>
     *                 functionalMapper.map(
     *                 mappedObject::setY,
     *                 toMapObject::getX,
     *                 X::getY
     *                 );
     */
    @Override
    public <SETTER> void map(final Consumer<SETTER> setter,
                             final Supplier<SETTER> getter) {
        SETTER nullable = getter.get();
        mapValue(setter, nullable);
    }

    /**
     * @param setter      Consumidor de resultados. Espera recibir una funcion con un parámetro SETTER y no retorna nada. (un SET)
     * @param getter      Proveedor de resultados. Espera recibir una funcion que no tenga parámetros y retorna un resultado del tipo SETTER
     * @param innerGetter Ejecuta una funcion sobre un valor del tipo retornado en el getter GETTER y retorna un resultado del tipo INNER_GETTER
     * @param <GETTER>    tipo generico -> X from getX()
     * @param <SETTER>    tipo generico -> Y from setY(y)
     */
    @Override
    public <GETTER, SETTER> void map(final Consumer<SETTER> setter, final Supplier<GETTER> getter,
                                     final Function<GETTER, SETTER> innerGetter) {
        SETTER nullable = getNullSafe(getter, innerGetter);
        mapValue(setter, nullable);
    }

    /**
     * Igual que el metodo
     * map(final Consumer<SETTER> setter, final Supplier<GETTER> getter, final Function<GETTER, SETTER> innerGetter)
     * Pero con un get interno más en la cadena de gets
     */
    @Override
    public <GETTER, INNER_GETTER, SETTER> void map(final Consumer<SETTER> setter, final Supplier<GETTER> getter,
                                                   final Function<GETTER, INNER_GETTER> innerGetter,
                                                   final Function<INNER_GETTER, SETTER> innerGetter2) {
        INNER_GETTER nullable = getNullSafe(getter, innerGetter);
        SETTER nullable2 = getNullSafeValue(nullable, innerGetter2);
        mapValue(setter, nullable2);
    }

    /**
     * Igual que el metodo
     * map(final Consumer<SETTER> setter, final Supplier<GETTER> getter, final Function<GETTER, SETTER> innerGetter)
     * Pero con 2 get interno más en la cadena de gets
     */
    @Override
    public <GETTER, INNER_GETTER, INNER_GETTER2, SETTER>
    void map(final Consumer<SETTER> setter, final Supplier<GETTER> getter,
             final Function<GETTER, INNER_GETTER> innerGetter,
             final Function<INNER_GETTER, INNER_GETTER2> innerGetter2,
             final Function<INNER_GETTER2, SETTER> innerGetter3) {
        INNER_GETTER nullable = getNullSafe(getter, innerGetter);
        INNER_GETTER2 nullable2 = getNullSafeValue(nullable, innerGetter2);
        SETTER nullable3 = getNullSafeValue(nullable2, innerGetter3);
        mapValue(setter, nullable3);
    }

    /**
     * Igual que el metodo
     * map(final Consumer<SETTER> setter, final Supplier<GETTER> getter, final Function<GETTER, SETTER> innerGetter)
     * Pero con 3 get interno más en la cadena de gets
     * <p>
     * setgetX().getY()
     */
    @Override
    public <GETTER, INNER_GETTER, INNER_GETTER2, INNER_GETTER3, SETTER>
    void map(final Consumer<SETTER> setter, final Supplier<GETTER> getter,
             final Function<GETTER, INNER_GETTER> innerGetter,
             final Function<INNER_GETTER, INNER_GETTER2> innerGetter2,
             final Function<INNER_GETTER2, INNER_GETTER3> innerGetter3,
             final Function<INNER_GETTER3, SETTER> innerGetter4) {
        INNER_GETTER nullable = getNullSafe(getter, innerGetter);
        INNER_GETTER2 nullable2 = getNullSafeValue(nullable, innerGetter2);
        INNER_GETTER3 nullable3 = getNullSafeValue(nullable2, innerGetter3);
        SETTER nullable4 = getNullSafeValue(nullable3, innerGetter4);
        mapValue(setter, nullable4);
    }

}
