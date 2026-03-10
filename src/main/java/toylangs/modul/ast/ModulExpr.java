package toylangs.modul.ast;

public sealed interface ModulExpr extends ModulNode permits ModulAdd, ModulMul, ModulNeg, ModulNum, ModulPow, ModulVar {

}
