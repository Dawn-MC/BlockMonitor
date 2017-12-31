package com.dawndevelop.helpers

import jdk.nashorn.internal.ir.Block
import org.spongepowered.api.Sponge
import org.spongepowered.api.block.BlockSnapshot
import org.spongepowered.api.data.DataContainer
import org.spongepowered.api.data.DataQuery
import org.spongepowered.api.data.Transaction
import org.spongepowered.api.entity.Entity
import org.spongepowered.api.entity.living.player.Player
import org.spongepowered.api.item.inventory.ItemStackSnapshot
import java.util.*

class DatacontainerHelper {
    companion object {
        fun setPlayer (dataContainer: DataContainer, player: Player) : DataContainer{
            dataContainer.set(DataQuery.of("Player"), player.toContainer())
            return dataContainer
        }

        fun getPlayer (dataContainer: DataContainer) : Optional<Player> {
            val playerOpt = dataContainer.getView(DataQuery.of("Player"))
            if (playerOpt.isPresent){
                val playerCont = playerOpt.get()
                return Sponge.getDataManager().deserialize(Player::class.java, playerCont)
            }
            return Optional.empty()
        }

        fun setEntity (dataContainer: DataContainer, entity: Entity) : DataContainer{
            dataContainer.set(DataQuery.of("Entity"), entity.toContainer())
            return dataContainer
        }

        fun getEntity (dataContainer: DataContainer) : Optional<Entity> {
            val entityOpt = dataContainer.getView(DataQuery.of("Entity"))
            if (entityOpt.isPresent){
                val entityCont = entityOpt.get()
                return  Sponge.getDataManager().deserialize(Entity::class.java, entityCont)
            }
            return Optional.empty()
        }

        fun setBlockTransactions (dataContainer: DataContainer, transactionList :List<Transaction<BlockSnapshot>>) : DataContainer {
            for ((id, transaction) in transactionList.withIndex()){
                dataContainer.set(DataQuery.of("BlockTransactions", id.toString()), transaction.toContainer())
                dataContainer.set(DataQuery.of("BlockTransactions", "maxId"), id)
            }
            return dataContainer
        }

        fun getBlockTransactions (dataContainer: DataContainer) : Optional<List<Transaction<BlockSnapshot>>> {
            if (dataContainer.contains(DataQuery.of("BlockTransactions"))){
                val maxIdOpt = dataContainer.getInt(DataQuery.of("BlockTransactions", "maxId"))
                if (maxIdOpt.isPresent){
                    var maxId = maxIdOpt.get()
                    val blockTransactions: MutableList<Transaction<BlockSnapshot>> = mutableListOf()
                    while (maxId >= 0){
                        val dataView = dataContainer.getView(DataQuery.of("BlockTransactions", maxId.toString()))
                        if (dataView.isPresent){
                            val blockTransactionOpt = Sponge.getDataManager().deserialize(Transaction::class.java, dataView.get())
                            if (blockTransactionOpt.isPresent){
                                if (blockTransactionOpt.get().default is BlockSnapshot){
                                    blockTransactions.add(blockTransactionOpt.get() as Transaction<BlockSnapshot>)
                                }
                            }
                        }

                        maxId--
                    }

                    return Optional.of(blockTransactions)
                }
            }

            return Optional.empty()
        }


        fun setItemStackTransactions (dataContainer: DataContainer, transactionList :List<Transaction<ItemStackSnapshot>>) : DataContainer {
            for ((id, transaction) in transactionList.withIndex()){
                dataContainer.set(DataQuery.of("ItemTransactions", id.toString()), transaction.toContainer())
                dataContainer.set(DataQuery.of("ItemTransactions", "maxId"), id)
            }
            return dataContainer
        }

        fun getItemStackTransactions (dataContainer: DataContainer) : Optional<List<Transaction<ItemStackSnapshot>>> {
            if (dataContainer.contains(DataQuery.of("ItemTransactions"))){
                val maxIdOpt = dataContainer.getInt(DataQuery.of("ItemTransactions", "maxId"))
                if (maxIdOpt.isPresent){
                    var maxId = maxIdOpt.get()
                    val itemTransactions: MutableList<Transaction<ItemStackSnapshot>> = mutableListOf()
                    while (maxId >= 0){
                        val dataView = dataContainer.getView(DataQuery.of("ItemTransactions", maxId.toString()))
                        if (dataView.isPresent){
                            val itemTransactionOpt = Sponge.getDataManager().deserialize(Transaction::class.java, dataView.get())
                            if (itemTransactionOpt.isPresent){
                                if (itemTransactionOpt.get().default is ItemStackSnapshot){
                                    itemTransactions.add(itemTransactionOpt.get() as Transaction<ItemStackSnapshot>)
                                }
                            }
                        }
                        maxId--
                    }
                    return Optional.of(itemTransactions)
                }
            }
            return Optional.empty()
        }
    }
}