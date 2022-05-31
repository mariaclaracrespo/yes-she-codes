(ns yes-she-codes.core-test
  (:require [clojure.test :refer [deftest is testing]]
            [schema.core :as s]
            [yes-she-codes.week3 :refer [ClienteSchema CartaoSchema CompraSchema]]))

(deftest ClienteSchema-test
  (testing "se o esquema aceita um cliente válido"
    (let [cliente-valido {:nome "Feiticeira Escarlate",
                          :cpf "000.111.222-33"
                          :email "feiticeira.poderosa@vingadoras.com.br"}]
      ;; Put expected first, e.g. `(is (= expected actual))`
      (is (= cliente-valido
             (s/validate ClienteSchema cliente-valido)))))

  (testing "se cliente com :nome nil é validado"
    (let [cliente-invalido {:nome nil,
                            :cpf "000.111.222-33"
                            :email "feiticeira.poderosa@vingadoras.com.br"}]
      (is (thrown? clojure.lang.ExceptionInfo
                   (s/validate ClienteSchema cliente-invalido))))))

(deftest CartaoSchema-test
  (testing "se o esquema aceita um cartão válido (v1)"
    (let [cartao-valido {:numero   1234123412341234,
                         :cvv      111,
                         :validade "2023-01",
                         :limite   1.000M,
                         :cliente  "000.111.222-33"}]
      (is (= cartao-valido
             (s/validate CartaoSchema cartao-valido)))))
  
  (testing "se o esquema aceita um cartão válido (v2)"
    (let [cartao-valido {:numero   1234123412341234,
                         :cvv      111,
                         :validade "2023-01",
                         :limite   1.000M,
                         :cliente  "000.111.222-33"}]
      ;; Since s/validate is documented to either return
      ;; its argument when it's valid and throw an exception
      ;; when it's not, we can just test for the truthiness
      ;; of the return value here.
      (is (s/validate CartaoSchema cartao-valido))))
  
  (testing "se o esquema aceita um cartão válido (v3)"
    ;; No need for a let-binding if we're only referencing
    ;; cartao-valido once
    (is (s/validate CartaoSchema (keys {:numero   1234123412341234,
                                        :cvv      111,
                                        :validade "2023-01",
                                        :limite   1.000M,
                                        :cliente  "000.111.222-33"})))))

(deftest CompraSchema-test
  (testing "se o esquema aceita uma compra válida"
    (let [compra-valida {:data "2022-01-01",
                         :valor 129.90M,
                         :estabelecimento "Outback",
                         :categoria "Alimentação",
                         :cartao 1234123412341234}]
      (is (= compra-valida
             (s/validate CompraSchema compra-valida))))))