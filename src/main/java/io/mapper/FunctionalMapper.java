package io.mapper;

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
public interface FunctionalMapper {




    /**
     * @param getter         Proveedor de resultados. Espera recibir una funcion que no tenga parámetros y retorna un resultado del tipo GETTER
     * @param innerGetter    Ejecuta una funcion sobre un valor del tipo retornado en el getter GETTER y retorna un resultado del tipo INNER_GETTER
     * @param <GETTER>       tipo generico -> X from getX()
     * @param <INNER_GETTER> tipo generico -> Z from getX().getZ(); (pero nullSafe)
     * @return
     */
    <GETTER, INNER_GETTER> INNER_GETTER getNullSafe(Supplier<GETTER> getter,
                                                    Function<GETTER, INNER_GETTER> innerGetter);

    /**
     * @param getter         Proveedor de resultados. Espera recibir una funcion que no tenga parámetros y retorna un resultado del tipo GETTER
     * @param innerGetter    Ejecuta una funcion sobre un valor del tipo retornado en el getter GETTER y retorna un resultado del tipo INNER_GETTER
     * @param <GETTER>       tipo generico -> X from getX()
     * @param <INNER_GETTER> tipo generico -> Z from getX().getZ(); (pero nullSafe)
     * @return
     */
    <GETTER, INNER_GETTER, INNER_INNER_GETTER> INNER_INNER_GETTER getNullSafe(Supplier<GETTER> getter,
                                                                              Function<GETTER, INNER_GETTER> innerGetter, Function<INNER_GETTER, INNER_INNER_GETTER> innerinnerGetter);

    <GETTER, INNER_GETTER> INNER_GETTER getNullSafeValue(GETTER nullableValue,
                                                         Function<GETTER, INNER_GETTER> innerGetter);


        /**
         * @param setter        Consumidor de resultados. Espera recibir una funcion con un parámetro Y y no retorna nada. (un SET)
         * @param nullableValue objeto del tipo que espera recibir el setter. Y
         * @param <SETTER>      tipo generico -> Y from setY(y)
         */
    <SETTER> void mapValue(Consumer<SETTER> setter, SETTER nullableValue);

    /**
     * <pre>
     *                 functionalMapper.map(
     *                 mappedObject::setY,
     *                 toMapObject::getY,
     *                 );
     *                </pre>
     *
     * @param setter   Consumidor de resultados. Espera recibir una funcion con un parámetro SETTER y no retorna nada. (un SET)
     * @param getter   Proveedor de resultados. Espera recibir una funcion que no tenga parámetros y retorna un resultado del tipo SETTER
     * @param <SETTER> tipo generico -> Y from setY(y)
     */
    <SETTER> void map(Consumer<SETTER> setter,
                      Supplier<SETTER> getter);

    /**
     * <pre>
     *                 functionalMapper.map(
     *                 mappedObject::setY,
     *                 toMapObject::getX,
     *                 X::getY
     *                 );
     *                </pre>
     * @param setter      Consumidor de resultados. Espera recibir una funcion con un parámetro SETTER y no retorna nada. (un SET)
     * @param getter      Proveedor de resultados. Espera recibir una funcion que no tenga parámetros y retorna un resultado del tipo SETTER
     * @param innerGetter Ejecuta una funcion sobre un valor del tipo retornado en el getter GETTER y retorna un resultado del tipo INNER_GETTER
     * @param <GETTER>    tipo generico -> X from getX()
     * @param <SETTER>    tipo generico -> Y from setY(y)
     */
    <GETTER, SETTER> void map(Consumer<SETTER> setter, Supplier<GETTER> getter,
                              Function<GETTER, SETTER> innerGetter);

    /**
     * Igual que el metodo
     * map(final Consumer<SETTER> setter, final Supplier<GETTER> getter, final Function<GETTER, SETTER> innerGetter)
     * Pero con un get interno más en la cadena de gets
     */
    <GETTER, INNER_GETTER, SETTER> void map(Consumer<SETTER> setter, Supplier<GETTER> getter,
                                            Function<GETTER, INNER_GETTER> innerGetter,
                                            Function<INNER_GETTER, SETTER> innerGetter2);

    /**
     * Igual que el metodo
     * map(final Consumer<SETTER> setter, final Supplier<GETTER> getter, final Function<GETTER, SETTER> innerGetter)
     * Pero con 2 get interno más en la cadena de gets
     */
    <GETTER, INNER_GETTER, INNER_GETTER2, SETTER>
    void map(Consumer<SETTER> setter, Supplier<GETTER> getter,
             Function<GETTER, INNER_GETTER> innerGetter,
             Function<INNER_GETTER, INNER_GETTER2> innerGetter2,
             Function<INNER_GETTER2, SETTER> innerGetter3);

    /**
     * Igual que el metodo
     * map(final Consumer<SETTER> setter, final Supplier<GETTER> getter, final Function<GETTER, SETTER> innerGetter)
     * Pero con 3 get interno más en la cadena de gets
     */
    <GETTER, INNER_GETTER, INNER_GETTER2, INNER_GETTER3, SETTER>
    void map(Consumer<SETTER> setter, Supplier<GETTER> getter,
             Function<GETTER, INNER_GETTER> innerGetter,
             Function<INNER_GETTER, INNER_GETTER2> innerGetter2,
             Function<INNER_GETTER2, INNER_GETTER3> innerGetter3,
             Function<INNER_GETTER3, SETTER> innerGetter4);

}
